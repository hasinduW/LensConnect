package backend.controller;

import backend.service.ProgressService;
import backend.service.QuizService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class ProgressController {
    @Autowired
    private ProgressService progressService;

    @Autowired
    private QuizService quizService;

    @GetMapping("/users/{userId}/progress")
    public Map<String, Object> getOverallProgress(@PathVariable Long userId) {
        return progressService.getOverallProgress(userId);
    }

    @GetMapping("/users/{userId}/progress/{courseId}")
    public Map<String, Object> getCourseProgress(
            @PathVariable Long userId,
            @PathVariable Long courseId) {
        return progressService.getCourseProgress(userId, courseId);
    }

    @GetMapping("/courses/{courseId}/quiz")
    public List<Map<String, Object>> getQuizQuestions(@PathVariable Long courseId) {
        return quizService.getQuizQuestions(courseId);
    }

    @PostMapping("/progress")
    public Map<String, Object> submitQuizAnswers(
            @RequestParam Long userId,
            @RequestParam Long courseId,
            @RequestBody Map<Long, Integer> answers) {
        return progressService.updateQuizProgress(userId, courseId, answers);
    }
}