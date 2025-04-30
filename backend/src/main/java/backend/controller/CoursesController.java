package backend.controller;

import backend.exception.CoursesNotFoundException;
import backend.model.courses.CoursesModel;
import backend.repository.CoursesRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.UUID;

@RestController
@CrossOrigin("http://localhost:3000")
@RequestMapping("/course")
public class CoursesController {

    private final String UPLOAD_DIR = "uploads/";

    @Autowired
    private CoursesRepository coursesRepository;

    @PostMapping("/updateCourse")
    public CoursesModel updateCourse(@RequestBody CoursesModel newCourse) {
        System.out.println("DEBUG - CourseName: " + newCourse.getCourseName());
        return coursesRepository.save(newCourse);
    }

    @PostMapping("/CourseImage")
    public String uploadImage(@RequestParam("file") MultipartFile file) {
        String folder = new File(UPLOAD_DIR).getAbsolutePath() + File.separator;
        String fileName = file.getOriginalFilename();

        if (fileName != null) {
            String fileExtension = fileName.substring(fileName.lastIndexOf("."));
            String uniqueFileName = UUID.randomUUID().toString() + fileExtension;
            fileName = uniqueFileName;
        }

        try {
            File uploadDir = new File(folder);
            if (!uploadDir.exists()) uploadDir.mkdirs();

            Path path = Paths.get(folder, fileName);
            file.transferTo(path.toFile());
            System.out.println("File saved at: " + path.toAbsolutePath());

        } catch (IOException e) {
            e.printStackTrace();
            return "error";
        }

        return fileName;
    }

    @GetMapping("/courses")
    public List<CoursesModel> getAllCourses() {
        return coursesRepository.findAll();
    }

    @GetMapping("/courses/{id}")
    public CoursesModel getCourseById(@PathVariable Long id) {
        return coursesRepository.findById(id)
                .orElseThrow(() -> new CoursesNotFoundException(id));
    }

    @PutMapping("/course/{id}")
    public CoursesModel updateCourse(
            @RequestPart(value = "itemDetails") String itemDetails,
            @RequestPart(value = "file", required = false) MultipartFile file,
            @PathVariable Long id
    ) {
        System.out.println("Course Details: " + itemDetails);
        if (file != null) {
            System.out.println("File received: " + file.getOriginalFilename());
        } else {
            System.out.println("No file uploaded");
        }

        ObjectMapper mapper = new ObjectMapper();
        CoursesModel newCourse;
        try {
            newCourse = mapper.readValue(itemDetails, CoursesModel.class);
        } catch (Exception e) {
            throw new RuntimeException("Error parsing course details", e);
        }

        return coursesRepository.findById(id).map(existingCourse -> {
            existingCourse.setCourseId(newCourse.getCourseId());
            existingCourse.setCourseName(newCourse.getCourseName());
            existingCourse.setCourseCategory(newCourse.getCourseCategory());
            existingCourse.setCourseDetails(newCourse.getCourseDetails());

            if (file != null && !file.isEmpty()) {
                String folder = new File(UPLOAD_DIR).getAbsolutePath() + File.separator;
                String courseImage = UUID.randomUUID().toString() + "_" + file.getOriginalFilename();

                try {
                    File uploadDir = new File(folder);
                    if (!uploadDir.exists()) uploadDir.mkdirs();
                    Path path = Paths.get(folder, courseImage);
                    file.transferTo(path.toFile());
                    existingCourse.setCourseImage(courseImage);
                } catch (IOException e) {
                    throw new RuntimeException("Error saving uploaded file", e);
                }
            }

            return coursesRepository.save(existingCourse);
        }).orElseThrow(() -> new CoursesNotFoundException(id));
    }

    //delete part
    @DeleteMapping("/course/{id}")
    public String deleteCourse(@PathVariable Long id) {
        // Check if course exists
        CoursesModel courseItem = coursesRepository.findById(id)
                .orElseThrow(() -> new CoursesNotFoundException(id));

        // Delete image if exists
        String courseImage = courseItem.getCourseImage();
        if (courseImage != null && !courseImage.isEmpty()) {
            File imageFile = new File("uploads" + File.separator + courseImage);
            if (imageFile.exists()) {
                if (imageFile.delete()) {
                    System.out.println("Image deleted successfully");
                } else {
                    System.out.println("Failed to delete image");
                }
            } else {
                System.out.println("Image file not found");
            }
        }

        // Delete course from repository
        coursesRepository.deleteById(id);

        return "Course with ID " + id + " and its image (if existed) were deleted.";
    }

    
}

