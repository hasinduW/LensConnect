package backend.model;

import jakarta.persistence.*;
//import lombok.Data;

@Entity
public class QuizOption {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "question_id")
    private QuizQuestion question;

    private int optionIndex;
    private String optionText;

    public int getOptionIndex() {
        return optionIndex;
    }

    public String getOptionText() {
        return optionText;
    }
}