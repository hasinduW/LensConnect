package backend.controller;

import backend.model.PostModel;
import backend.repository.PostRepository;
import backend.service.LikeService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Controller
public class PostController {

    private final PostRepository postRepo;
    private final LikeService likeService;

    public PostController(PostRepository postRepo,
            LikeService likeService) {
        this.postRepo = postRepo;
        this.likeService = likeService;
    }

    @GetMapping("/")
    public String listPosts(Model model) {
        // 1. load all posts
        List<PostModel> posts = postRepo.findAll();

        // 2. build a map of postId → likeCount
        Map<Long, Long> likeCounts = posts.stream()
                .collect(Collectors.toMap(
                        PostModel::getId,
                        p -> likeService.getLikeCount(p.getId())));

        // 3. put both into the model
        model.addAttribute("posts", posts);
        model.addAttribute("likeCounts", likeCounts);
        return "posts"; // resolves to posts.html
    }
}
