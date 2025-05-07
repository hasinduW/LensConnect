package backend.model;

import jakarta.persistence.*;
import java.time.Instant;

@Entity
@Table(name = "comments")
public class CommentModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "post_id")
    private PostModel post;

    // (For now) we’ll pass userId in the URL, so we store it here as a simple Long
    private Long userId;

    @Column(nullable = false, length = 1000)
    private String text;

    private Instant createdAt = Instant.now();

    // Constructors
    public CommentModel() {
    }

    public CommentModel(PostModel post, Long userId, String text) {
        this.post = post;
        this.userId = userId;
        this.text = text;
    }

    public Long getId() {
        return id;
    }

    public PostModel getPost() {
        return post;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }
}
