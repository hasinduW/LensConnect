package backend.repository;

import backend.model.LikeModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LikeRepository extends JpaRepository<LikeModel, Long> {
    long countByPostId(Long postId);

    boolean existsByPostIdAndUserId(Long postId, Long userId);
}
