package backend.repository;

import backend.model.UserProgress;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface UserProgressRepository extends JpaRepository<UserProgress, Long> {

    List<UserProgress> findByUserId(Long userId);
    //Optional<UserProgress> findByUserIdAndCourseId(Long userId, Long courseId);

    @Query("SELECT up FROM UserProgress up WHERE up.userId = :userId AND up.course.id = :courseId")
    Optional<UserProgress> findByUserIdAndCourseId(@Param("userId") Long userId,
                                                   @Param("courseId") Long courseId);

    int countByUserIdAndIsCompleted(Long userId, boolean isCompleted);
    int countByUserId(Long userId);
}