package backend.service;

import backend.model.CommentModel;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.security.access.AccessDeniedException;
import backend.model.PostModel;
import backend.repository.CommentRepository;
import backend.repository.PostRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
public class CommentService {

    private final CommentRepository commentRepo;
    private final PostRepository postRepo;

    public CommentService(CommentRepository commentRepo,
            PostRepository postRepo) {
        this.commentRepo = commentRepo;
        this.postRepo = postRepo;
    }

    public List<CommentModel> getCommentsForPost(Long postId) {
        return commentRepo.findByPostIdOrderByCreatedAtAsc(postId);
    }

    @Transactional
    public CommentModel addComment(Long postId, Long userId, String text) {
        PostModel post = postRepo.findById(postId)
                .orElseThrow(() -> new EntityNotFoundException("PostModel not found: " + postId));
        CommentModel c = new CommentModel(post, userId, text);
        return commentRepo.save(c);
    }

    @Transactional
    public CommentModel editComment(Long commentId, Long userId, String newText) {
        CommentModel c = commentRepo.findById(commentId)
                .orElseThrow(() -> new EntityNotFoundException("CommentModel not found: " + commentId));
        // only allow the author to edit
        if (!c.getUserId().equals(userId)) {
            throw new AccessDeniedException("Not the comment author");
        }
        c.setText(newText);
        return commentRepo.save(c);
    }

    /**
     * Delete a comment if it belongs to the given user.
     * Throws EntityNotFoundException if missing, AccessDeniedException if not the
     * owner.
     */
    @Transactional
    public void deleteComment(Long commentId, Long userId) {
        CommentModel c = commentRepo.findById(commentId)
                .orElseThrow(() -> new EntityNotFoundException("CommentModel not found: " + commentId));
        if (!c.getUserId().equals(userId)) {
            throw new AccessDeniedException("Cannot delete comment you did not author");
        }
        commentRepo.delete(c);
    }
}
