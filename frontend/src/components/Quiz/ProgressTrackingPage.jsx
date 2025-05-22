import React, { useState, useEffect } from 'react';
import { getOverallProgress, getCourseProgress } from './progressService';
import CourseCard from './CourseCard';
import './ProgressTrackingPage.css';


const ProgressTrackingPage = () => {
  const [overallProgress, setOverallProgress] = useState(null);
  const [courseProgress, setCourseProgress] = useState({});
  const [selectedCourse, setSelectedCourse] = useState(null);

  useEffect(() => {
    const fetchProgress = async () => {
      const userId = localStorage.getItem('userId') || '1'; // Assuming user is logged in
      const overall = await getOverallProgress(userId);
      setOverallProgress(overall);
    };
    fetchProgress();
  }, []);

  const handleCourseSelect = async (courseId) => {
    const userId = localStorage.getItem('userId') || '1'; // Assuming user is logged in
    const progress = await getCourseProgress(userId, courseId);
    setCourseProgress(prev => ({
      ...prev,
      [courseId]: progress
    }));
    setSelectedCourse(courseId);
  };

  return (
    <div className="progress-container">
      <h1>Your Learning Progress</h1>
      
      {overallProgress && (
        <div className="overall-progress">
          <h2>Overall Progress</h2>
          <p>Completed Courses: {overallProgress.completedCourses}</p>
          <p>Total Courses: {overallProgress.totalCourses}</p>
          <div className="progress-bar">
            <div 
              className="progress-fill"
              style={{ width: `${overallProgress.completionPercentage}%` }}
            ></div>
          </div>
          <p>{Math.round(overallProgress.completionPercentage)}% Complete</p>
        </div>
      )}
      
      <div className="courses-section">
        <h2>Your Courses</h2>
        <div className="course-cards">
          {overallProgress?.courses?.map(course => (
            <CourseCard
              key={course.id}
              course={course}
              onClick={() => handleCourseSelect(course.id)}
              isSelected={selectedCourse === course.id}
            />
          ))}
        </div>
      </div>
      
      {selectedCourse && courseProgress[selectedCourse] && (
        <div className="detailed-progress">
          <h3>{courseProgress[selectedCourse].courseName}</h3>
          <p>Status: {courseProgress[selectedCourse].isCompleted ? 'Completed' : 'In Progress'}</p>
          {courseProgress[selectedCourse].quizResults && (
            <div className="quiz-results">
              <h4>Quiz Results</h4>
              <p>Score: {courseProgress[selectedCourse].quizResults.score}%</p>
              <p>Correct Answers: {courseProgress[selectedCourse].quizResults.correctAnswers}</p>
              <p>Total Questions: {courseProgress[selectedCourse].quizResults.totalQuestions}</p>
            </div>
          )}
        </div>
      )}
    </div>
  );
};

export default ProgressTrackingPage;