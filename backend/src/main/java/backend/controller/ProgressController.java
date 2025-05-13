package backend.controller;

import com.example.photolearn.model.UserProgress;
import com.example.photolearn.service.ProgressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class ProgressController {
    private final ProgressService progressService;

    @Autowired
    public ProgressController(ProgressService progressService) {
        this.progressService = progressService;
    }

    @GetMapping("/users/{userId}/progress")
    public ResponseEntity<Map<String, Object>> getUserProgress(@PathVariable Long userId) {
        Map<String, Object> progress = progressService.getUserProgress(userId);
        return ResponseEntity.ok(progress);
    }

    @GetMapping("/users/{userId}/progress/{courseId}")
    public ResponseEntity<UserProgress> getCourseProgress(
            @PathVariable Long userId,
            @PathVariable Long courseId) {
        UserProgress progress = progressService.getCourseProgress(userId, courseId);
        return ResponseEntity.ok(progress);
    }

    @PostMapping("/progress")
    public ResponseEntity<UserProgress> saveQuizProgress(
            @RequestParam Long userId,
            @RequestParam Long courseId,
            @RequestBody List<Integer> answers) {
        UserProgress progress = progressService.saveQuizProgress(userId, courseId, answers);
        return ResponseEntity.ok(progress);
    }
}