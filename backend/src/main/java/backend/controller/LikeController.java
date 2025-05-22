package backend.controller;

import backend.service.LikeService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import java.util.Map;

@RestController
@RequestMapping("/posts/{postId}/likes")
public class LikeController {

    private final LikeService likeService;

    public LikeController(LikeService likeService) {
        this.likeService = likeService;
    }

    /**
     * GET /posts/{postId}/likes
     * Returns JSON: { "count": 5 }
     */
    @GetMapping
    public Map<String, Long> getLikeCount(@PathVariable Long postId) {
        long count = likeService.getLikeCount(postId);
        return Map.of("count", count);
    }

    /**
     * POST /posts/{postId}/likes?userId=123
     * Creates a like for the given user on the given post.
     * Responds HTTP 201 Created on success.
     */
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void addLike(
            @PathVariable Long postId,
            @RequestParam Long userId) {
        likeService.likePost(postId, userId);
    }
}
