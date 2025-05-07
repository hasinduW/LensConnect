package backend.service;

import backend.model.LikeModel;
import backend.model.PostModel;
import backend.model.UserModel;
import backend.repository.LikeRepository;
import backend.repository.PostRepository;
import backend.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class LikeService {

    private final LikeRepository likeRepo;
    private final PostRepository postRepo;
    private final UserRepository userRepo;

    public LikeService(LikeRepository likeRepo,
            PostRepository postRepo,
            UserRepository userRepo) {
        this.likeRepo = likeRepo;
        this.postRepo = postRepo;
        this.userRepo = userRepo;
    }

    /**
     * Return the total number of likes for a given post.
     */
    public long getLikeCount(Long postId) {
        return likeRepo.countByPostId(postId);
    }

    /**
     * Create a like for this user & post if one does not already exist.
     * 
     * @throws EntityNotFoundException if postId or userId are invalid
     */
    @Transactional
    public void likePost(Long postId, Long userId) {
        // avoid duplicate likes
        if (likeRepo.existsByPostIdAndUserId(postId, userId)) {
            return;
        }

        // load PostModel and UserModel (throws if missing)
        PostModel post = postRepo.findById(postId)
                .orElseThrow(() -> new EntityNotFoundException("PostModel not found: " + postId));
        UserModel user = userRepo.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("UserModel not found: " + userId));

        // create and save the likeModel
        LikeModel likeModel = new LikeModel();
        likeModel.setPost(post);
        likeModel.setUser(user);
        likeRepo.save(likeModel);
    }
}
