package backend.service;

import backend.model.*;
import backend.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ProgressService {
    private final UserProgressRepository userProgressRepository;
    private final QuizRepository quizRepository;

    @Autowired
    public ProgressService(UserProgressRepository userProgressRepository, QuizRepository quizRepository) {
        this.userProgressRepository = userProgressRepository;
        this.quizRepository = quizRepository;
    }

    public Map<String, Object> getUserProgress(Long userId) {
        List<UserProgress> userProgressList = userProgressRepository.findByUserId(userId);

        int totalCourses = userProgressList.size();
        int completedCourses = (int) userProgressList.stream().filter(UserProgress::getCompleted).count();
        double completionRate = totalCourses > 0 ? (completedCourses * 100.0) / totalCourses : 0;

        Map<String, Object> result = new HashMap<>();
        result.put("totalCourses", totalCourses);
        result.put("completedCourses", completedCourses);
        result.put("completionRate", completionRate);
        result.put("courses", userProgressList);

        return result;
    }

    public UserProgress getCourseProgress(Long userId, Long courseId) {
        return userProgressRepository.findByUserIdAndCourseId(userId, courseId);
    }

    @Transactional
    public UserProgress saveQuizProgress(Long userId, Long courseId, List<Integer> answers) {
        Quiz quiz = quizRepository.findByCourseId(courseId);
        if (quiz == null) {
            throw new RuntimeException("Quiz not found for course");
        }

        List<QuizQuestion> questions = quiz.getQuestions();
        if (questions.size() != answers.size()) {
            throw new RuntimeException("Number of answers doesn't match number of questions");
        }

        // Calculate score
        int correctAnswers = 0;
        for (int i = 0; i < questions.size(); i++) {
            if (questions.get(i).getCorrectAnswerIndex().equals(answers.get(i))) {
                correctAnswers++;
            }
        }

        double score = (correctAnswers * 100.0) / questions.size();
        boolean completed = score >= 80; // Assuming 80% is passing

        // Save or update progress
        UserProgress progress = userProgressRepository.findByUserIdAndCourseId(userId, courseId);
        if (progress == null) {
            progress = new UserProgress();
            progress.setUserId(userId);
            progress.setCourseId(courseId);
        }

        progress.setScore(score);
        progress.setCompleted(completed);
        progress.setLastUpdated(LocalDateTime.now());

        // Store answers as JSON (simplified)
        progress.setAnswersJson(answers.toString());

        return userProgressRepository.save(progress);
    }
}