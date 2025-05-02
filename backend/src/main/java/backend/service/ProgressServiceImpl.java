package backend.service;

import backend.model.Progress;
import backend.repository.ProgressRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProgressServiceImpl implements ProgressService {

    private final ProgressRepository progressRepository;

    public ProgressServiceImpl(ProgressRepository progressRepository) {
        this.progressRepository = progressRepository;
    }

    @Override
    public Progress getProgress(Long userId, Long courseId) {
        return progressRepository.findByUserIdAndCourseId(userId, courseId);
    }

    @Override
    public List<Progress> getAllProgressForUser(Long userId) {
        return progressRepository.findByUserId(userId);
    }

    @Override
    public Progress updateProgress(Progress progress) {
        return progressRepository.save(progress);
    }

    @Override
    public void deleteProgress(Long progressId) {
        progressRepository.deleteById(progressId);
    }

    @Override
    public Progress createProgress(Progress progress) {
        return progressRepository.save(progress);
    }
}