package backend.controller;

import backend.exception.PostNotFoundException;
import backend.model.PostModel;
import backend.model.UserModel;
import backend.repository.PostRepository;
import backend.service.LikeService;

import com.fasterxml.jackson.databind.ObjectMapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.List;

@RestController
@CrossOrigin("http://localhost:3000")
public class PostController {

    @Autowired
    private PostRepository postRepository;

    @Autowired(required = false)
    private LikeService likeService; // Optional – only if like functionality exists

    private final String UPLOAD_DIR = "src/main/uploads/";

    // Create new post
    @PostMapping("/posts")
    public PostModel newPostModel(@RequestBody PostModel newPostModel) {
        return postRepository.save(newPostModel);
    }

    // Upload post image
    @PostMapping("/posts/postImg")
    public String postImage(@RequestParam("file") MultipartFile file) {
        String postImage = file.getOriginalFilename();
        try {
            File dir = new File(UPLOAD_DIR);
            if (!dir.exists()) dir.mkdir();
            file.transferTo(Paths.get(UPLOAD_DIR + postImage));
        } catch (IOException e) {
            e.printStackTrace();
            return "Error uploading file: " + postImage;
        }
        return postImage;
    }

    // Get all posts
    @GetMapping("/posts")
    public List<PostModel> getAllPosts() {
        return postRepository.findAll();
    }

    // Get single post by ID
    @GetMapping("/posts/{id}")
    public PostModel getPostId(@PathVariable Long id) {
        return postRepository.findById(id)
                .orElseThrow(() -> new PostNotFoundException(id));
    }

    // Update post
    @PutMapping("/posts/{id}")
    public PostModel updatePost(
            @RequestPart(value = "postDetails") String postDetails,
            @RequestPart(value = "file", required = false) MultipartFile file,
            @PathVariable Long id) {

        ObjectMapper mapper = new ObjectMapper();
        PostModel newPost;
        try {
            newPost = mapper.readValue(postDetails, PostModel.class);
        } catch (Exception e) {
            throw new RuntimeException("Error parsing postDetails", e);
        }

        return postRepository.findById(id).map(existingPost -> {
            existingPost.setPostId(newPost.getPostId());
            existingPost.setPostTitle(newPost.getPostTitle());
            existingPost.setPostDescription(newPost.getPostDescription());
            existingPost.setPostCategory(newPost.getPostCategory());

            if (file != null && !file.isEmpty()) {
                String postImage = file.getOriginalFilename();
                try {
                    file.transferTo(Paths.get(UPLOAD_DIR + postImage));
                    existingPost.setPostImage(postImage);
                } catch (IOException e) {
                    throw new RuntimeException("Error saving uploaded file", e);
                }
            }
            return postRepository.save(existingPost);
        }).orElseThrow(() -> new PostNotFoundException(id));
    }

    // Serve uploaded images
    @GetMapping("/uploads/{filename}")
    public ResponseEntity<FileSystemResource> getImage(@PathVariable String filename) {
        File file = new File(UPLOAD_DIR + filename);
        if (!file.exists()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(new FileSystemResource(file));
    }
}
