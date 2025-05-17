package backend.model;

import jakarta.persistence.*;
//import lombok.Data;

import java.util.List;

@Entity

public class Course {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String description;

    @OneToMany(mappedBy = "course", cascade = CascadeType.ALL)
    private List<QuizQuestion> quizQuestions;

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}