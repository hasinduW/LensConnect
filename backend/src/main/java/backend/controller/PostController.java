package backend.controller;

import backend.exception.PostNotFoundException;
import backend.model.PostModel;
import backend.repository.PostRepository;
import ch.qos.logback.core.net.SyslogOutputStream;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;
import java.io.IOException;
import java.lang.runtime.ObjectMethods;
import java.nio.file.Paths;
import java.util.List;

@RestController
@CrossOrigin("http://localhost:3000")  //  Correct URL for React frontend

public class PostController {

    @Autowired
    private PostRepository postRepository;


    //  Create new post (text data only)
    @PostMapping("/posts")
    public PostModel newPostModel(@RequestBody PostModel newPostModel) {
        return postRepository.save(newPostModel);   //get data from model and save to database
    }

    //Upload post image
    @PostMapping("/posts/postImg")
    public String postImage(@RequestParam("file") MultipartFile file) {

        String folder = "src/main/uploads/"; // Image save folder
        String postImage = file.getOriginalFilename(); //image name in database

        try {
            File uploadDir = new File(folder);
            if (!uploadDir.exists()) {
                uploadDir.mkdir();  // Create folder if not exists
            }

            //FIx Proper path joining
            file.transferTo(Paths.get(folder+postImage));

        } catch (IOException e) {
            e.printStackTrace();
            return "Error uploading file; " + postImage;
        }

        return postImage;
    }

    // Get all posts
    @GetMapping("/posts")
     List<PostModel> getAllPosts() {
        return postRepository.findAll();
    }

    //  Get post by ID
    @GetMapping("/posts/{id}")
   PostModel getPostId(@PathVariable Long id) {
        return postRepository.findById(id).orElseThrow(() -> new PostNotFoundException(id));
    }


    // Serve uploaded images
    private final String UPLOAD_DIR = "src/main/uploads/";

    @GetMapping("/uploads/{filename}")
    public ResponseEntity<FileSystemResource> getImage(@PathVariable String filename) {
        File file = new File(UPLOAD_DIR + filename);
        if (!file.exists()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(new FileSystemResource(file));
    }


//update function
@PutMapping("/posts/{id}")
public PostModel updatePost (
        @RequestPart(value = "postDetails") String postDetails,
        @RequestPart(value = "file", required =false) MultipartFile file,
        @PathVariable Long id
){
    System.out.println("Post Details: " + postDetails);
    if(file !=null){
        System.out.println("File received: "+file.getOriginalFilename());

    }else {
        System.out.println("No file uploaded");
    }

    ObjectMapper mapper = new ObjectMapper();
    PostModel newPost;
    try{
        newPost =mapper.readValue(postDetails,PostModel.class);
    }catch(Exception e){
        throw new RuntimeException("Error parsing postDetails", e);
    }
    return postRepository.findById(id).map( existingPost-> {
        existingPost.setPostId(newPost.getPostId());
        existingPost.setPostTitle(newPost.getPostTitle());
        existingPost.setPostDescription(newPost.getPostDescription());
        existingPost.setPostCategory(newPost.getPostCategory());


        if(file != null && !file.isEmpty()){
            String folder = "src/main/uploads/";
            String postImage = file.getOriginalFilename();
            try{
                file.transferTo(Paths.get(folder + postImage));
                existingPost.setPostImage(postImage);
            }catch (IOException e){
                throw new RuntimeException("Error Saving uploded file", e);
            }
        }
        return postRepository.save(existingPost);

    }).orElseThrow(()-> new PostNotFoundException(id));

    }
    }