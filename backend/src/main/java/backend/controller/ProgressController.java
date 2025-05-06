package backend.controller;

import backend.model.Progress;
import backend.service.ProgressService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/progress")
public class ProgressController {

    private final ProgressService progressService;

    public ProgressController(ProgressService progressService) {
        this.progressService = progressService;
    }

    // GET - Get all progress for a user
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<Progress>> getAllUserProgress(@PathVariable Long userId) {
        return ResponseEntity.ok(progressService.getAllProgressForUser(userId));
    }

    // GET - Get specific course progress for a user
    @GetMapping("/user/{userId}/course/{courseId}")
    public ResponseEntity<Progress> getUserCourseProgress(
            @PathVariable Long userId,
            @PathVariable Long courseId) {
        Progress progress = progressService.getProgress(userId, courseId);
        return progress != null ? ResponseEntity.ok(progress) : ResponseEntity.notFound().build();
    }

    // POST - Create new progress record
    @PostMapping
    public ResponseEntity<Progress> createProgress(@RequestBody Progress progress) {
        return ResponseEntity.ok(progressService.createProgress(progress));
    }

    // PUT - Update progress
    @PutMapping("/{id}")
    public ResponseEntity<Progress> updateProgress(
            @PathVariable Long id,
            @RequestBody Progress progress) {
        progress.setId(id);
        return ResponseEntity.ok(progressService.updateProgress(progress));
    }

    // DELETE - Remove progress record
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProgress(@PathVariable Long id) {
        progressService.deleteProgress(id);
        return ResponseEntity.noContent().build();
    }
}