package backend.repository;

import backend.model.UserProgress;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

public interface UserProgressRepository extends JpaRepository<UserProgress, Long> {
    List<UserProgress> findByUserId(Long userId);
    Optional<UserProgress> findByUserIdAndCourseId(Long userId, Long courseId);
    int countByUserIdAndIsCompleted(Long userId, boolean isCompleted);
    int countByUserId(Long userId);
}