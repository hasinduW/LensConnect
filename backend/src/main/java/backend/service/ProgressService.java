package backend.service;

import backend.model.Progress;

import java.util.List;

public interface ProgressService {
    Progress getProgress(Long userId, Long courseId);
    List<Progress> getAllProgressForUser(Long userId);
    Progress updateProgress(Progress progress);
    void deleteProgress(Long progressId);
    Progress createProgress(Progress progress);
}