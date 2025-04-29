package backend.controller;

import backend.exception.CoursesNotFoundException;
import backend.model.courses.CoursesModel;
import backend.repository.CoursesRepository;
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
            return "error"; // fallback error message
        }

        return fileName; // just return filename
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
}
