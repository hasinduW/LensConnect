package backend.service;

import backend.model.QuizOption;
import backend.model.QuizQuestion;
import backend.repository.QuizQuestionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class QuizService {
    @Autowired
    private QuizQuestionRepository quizQuestionRepository;

    public List<Map<String, Object>> getQuizQuestions(Long courseId) {
        List<QuizQuestion> questions = quizQuestionRepository.findByCourseId(courseId);

        return questions.stream()
                .map(question -> {
                    Map<String, Object> questionMap = new HashMap<>();
                    questionMap.put("id", question.getId());
                    questionMap.put("courseId", courseId);

                    if (question.getCourse() != null) {
                        questionMap.put("courseName", question.getCourse().getName());
                    }

                    questionMap.put("text", question.getQuestionText());

                    List<String> options = question.getOptions().stream()
                            .sorted(Comparator.comparingInt(QuizOption::getOptionIndex))
                            .map(QuizOption::getOptionText)
                            .collect(Collectors.toList());

                    questionMap.put("options", options);
                    return questionMap;
                })
                .collect(Collectors.toList());
    }
}