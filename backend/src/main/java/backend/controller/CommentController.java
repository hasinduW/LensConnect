package backend.controller;

import backend.model.CommentModel;
import backend.service.CommentService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class CommentController {

    private final CommentService commentService;

    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    // GET /posts/{postId}/comments
    @GetMapping("/posts/{postId}/comments")
    public List<CommentModel> getComments(@PathVariable Long postId) {
        return commentService.getCommentsForPost(postId);
    }

    // POST /posts/{postId}/comments?userId=123
    @PostMapping("/posts/{postId}/comments")
    @ResponseStatus(HttpStatus.CREATED)
    public CommentModel addComment(
            @PathVariable Long postId,
            @RequestParam Long userId,
            @RequestBody String text) {
        return commentService.addComment(postId, userId, text);
    }

    // PUT /comments/{commentId}?userId=123
    @PutMapping("/comments/{commentId}")
    public CommentModel editComment(
            @PathVariable Long commentId,
            @RequestParam Long userId,
            @RequestBody String newText) {
        return commentService.editComment(commentId, userId, newText);
    }

    /**
     * DELETE /comments/{commentId}?userId=…
     * Returns 204 No Content on success.
     */
    @DeleteMapping("/comments/{commentId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteComment(
            @PathVariable Long commentId,
            @RequestParam Long userId) {
        commentService.deleteComment(commentId, userId);
    }
}
