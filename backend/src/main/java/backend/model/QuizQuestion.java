package backend.model;

import jakarta.persistence.*;
//import lombok.Data;

import java.util.List;

@Entity
public class QuizQuestion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "course_id")
    private Course course;

    private String questionText;
    private int correctAnswerIndex;

    @OneToMany(mappedBy = "question", cascade = CascadeType.ALL)
    private List<QuizOption> options;

    // Add these methods if not present
    public Long getId() {
        return id;
    }

    public Course getCourse() {
        return course;
    }

    public int getCorrectAnswerIndex() {
        return correctAnswerIndex;
    }

    public String getQuestionText() {
        return questionText;
    }

    public List<QuizOption> getOptions() {
        return options;
    }

}