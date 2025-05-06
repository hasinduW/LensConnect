package backend.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

@Entity
public class PostModel {
    @Id
    @GeneratedValue
    private Long id;
    private String postId;
    private String postImage;
    private String postTitle;
    private String postDescription;
    private String postCategory;


    public PostModel(){
    }



    //getters and setters


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

    public PostModel(Long id, String postId, String postImage, String postTitle, String postDescription, String postCategory) {
        this.id = id;
        this.postId = postId;
        this.postImage = postImage;
        this.postTitle = postTitle;
        this.postDescription = postDescription;
        this.postCategory = postCategory;
    }
}
