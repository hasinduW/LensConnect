package backend.model.courses;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

@Entity
public class CoursesModel {

    @Id
    @GeneratedValue
    private Long id;

    private String courseId;
    private String courseImage;
    private String courseName;
    private String courseCategory;
    private String courseDetails;

    public CoursesModel() {}

    public CoursesModel(Long id, String courseId, String courseImage, String courseName, String courseCategory, String courseDetails) {
        this.id = id;
        this.courseId = courseId;
        this.courseImage = courseImage;
        this.courseName = courseName;
        this.courseCategory = courseCategory;
        this.courseDetails = courseDetails;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCourseId() {
        return courseId;
    }

    public void setCourseId(String courseId) {
        this.courseId = courseId;
    }

    public String getCourseImage() {
        return courseImage;
    }

    public void setCourseImage(String courseImage) {
        this.courseImage = courseImage;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public String getCourseCategory() {
        return courseCategory;
    }

    public void setCourseCategory(String courseCategory) {
        this.courseCategory = courseCategory;
    }

    public String getCourseDetails() {
        return courseDetails;
    }

    public void setCourseDetails(String courseDetails) {
        this.courseDetails = courseDetails;
    }
}
