/* File: src/main/java/com/example/socialapp/model/PostModel.java */
package backend.model;

import jakarta.persistence.*;

@Entity
public class PostModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String content;

    // No-arg constructor required by JPA
    public PostModel() {
    }

    // Convenience constructor
    public PostModel(String content) {
        this.content = content;
    }

    // Getters and setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}