package backend.service;

import backend.exception.ResourceNotFoundException;
import backend.model.*;
import backend.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ProgressService {
    @Autowired
    private UserProgressRepository userProgressRepository;

    @Autowired
    private CourseRepository courseRepository;

    @Autowired
    private QuizQuestionRepository quizQuestionRepository;

    public Map<String, Object> getOverallProgress(Long userId) {
        Map<String, Object> response = new HashMap<>();

        int totalCourses = userProgressRepository.countByUserId(userId);
        int completedCourses = userProgressRepository.countByUserIdAndIsCompleted(userId, true);
        double completionPercentage = totalCourses > 0 ? (completedCourses * 100.0) / totalCourses : 0;

        List<UserProgress> allProgress = userProgressRepository.findByUserId(userId);
        List<Map<String, Object>> courses = allProgress.stream()
                .map(progress -> {
                    Map<String, Object> courseMap = new HashMap<>();
                    Course course = progress.getCourse();
                    if (course != null) {
                        courseMap.put("id", course.getId());
                        courseMap.put("name", course.getName());
                        courseMap.put("isCompleted", progress.getIsCompleted());
                        courseMap.put("completionPercentage", progress.getIsCompleted() ? 100 : 0);
                    }
                    return courseMap;
                })
                .collect(Collectors.toList());

        response.put("totalCourses", totalCourses);
        response.put("completedCourses", completedCourses);
        response.put("completionPercentage", completionPercentage);
        response.put("courses", courses);

        return response;
    }
    //2
    public Map<String, Object> getCourseProgress(Long userId, Long courseId) {
        UserProgress progress = userProgressRepository.findByUserIdAndCourseId(userId, courseId)
                .orElseThrow(() -> new ResourceNotFoundException("Progress not found"));

        Map<String, Object> response = new HashMap<>();
        Course course = progress.getCourse();
        if (course != null) {
            response.put("courseId", courseId);
            response.put("courseName", course.getName());
            response.put("isCompleted", progress.getIsCompleted());

            QuizResult quizResult = progress.getQuizResult();
            if (quizResult != null) {
                Map<String, Object> quizResults = new HashMap<>();
                quizResults.put("score", quizResult.getScore());
                quizResults.put("correctAnswers", quizResult.getCorrectAnswers());
                quizResults.put("totalQuestions", quizResult.getTotalQuestions());
                response.put("quizResults", quizResults);
            }
        }
        return response;
    }
    //3,4
    public Map<String, Object> updateQuizProgress(Long userId, Long courseId, Map<Long, Integer> answers) {
        Course course = courseRepository.findById(courseId)
                .orElseThrow(() -> new ResourceNotFoundException("Course not found"));

        UserProgress progress = userProgressRepository.findByUserIdAndCourseId(userId, courseId)
                .orElseGet(() -> {
                    UserProgress newProgress = new UserProgress();
                    newProgress.setUserId(userId);
                    newProgress.setCourse(course);
                    return newProgress;
                });

        // Calculate score
        List<QuizQuestion> questions = quizQuestionRepository.findByCourseId(courseId);
        int correctAnswers = 0;

        for (QuizQuestion question : questions) {
            Integer selectedAnswer = answers.get(question.getId());
            if (selectedAnswer != null && selectedAnswer == question.getCorrectAnswerIndex()) {
                correctAnswers++;
            }
        }

        double score = (double) correctAnswers / questions.size() * 100;

        // Update progress
        progress.setIsCompleted(true);
        progress.setCompletionDate(LocalDateTime.now());

        // Save quiz result
        QuizResult quizResult = progress.getQuizResult();
        if (quizResult == null) {
            quizResult = new QuizResult();
            quizResult.setProgress(progress);
            progress.setQuizResult(quizResult);
        }

        quizResult.setScore(score);
        quizResult.setCorrectAnswers(correctAnswers);
        quizResult.setTotalQuestions(questions.size());
        quizResult.setSubmittedAt(LocalDateTime.now());

        userProgressRepository.save(progress);

        Map<String, Object> response = new HashMap<>();
        response.put("score", score);
        response.put("correctAnswers", correctAnswers);
        response.put("totalQuestions", questions.size());
        response.put("isCompleted", true);

        return response;
    }
}