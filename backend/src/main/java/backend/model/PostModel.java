package backend.model;

import jakarta.persistence.*;

@Entity
public class PostModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // From Tharushi/Like&Comment (optional)
    private String content;

    // From main branch
    private String postId;
    private String postImage;
    private String postTitle;
    private String postDescription;
    private String postCategory;

    // No-arg constructor
    public PostModel() {
    }

    // Convenience constructor (from Like & Comment)
    public PostModel(String content) {
        this.content = content;
    }

    // Full constructor (main branch)
    public PostModel(Long id, String postId, String postImage, String postTitle, String postDescription, String postCategory) {
        this.id = id;
        this.postId = postId;
        this.postImage = postImage;
        this.postTitle = postTitle;
        this.postDescription = postDescription;
        this.postCategory = postCategory;
    }

    // Getters and setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPostId() {
        return postId;
    }

    public void setPostId(String postId) {
        this.postId = postId;
    }

    public String getPostImage() {
        return postImage;
    }

    public void setPostImage(String postImage) {
        this.postImage = postImage;
    }

    public String getPostTitle() {
        return postTitle;
    }

    public void setPostTitle(String postTitle) {
        this.postTitle = postTitle;
    }

    public String getPostDescription() {
        return postDescription;
    }

    public void setPostDescription(String postDescription) {
        this.postDescription = postDescription;
    }

    public String getPostCategory() {
        return postCategory;
    }

    public void setPostCategory(String postCategory) {
        this.postCategory = postCategory;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
