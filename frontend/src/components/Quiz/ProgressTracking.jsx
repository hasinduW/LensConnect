import React, { useState, useEffect } from 'react';
import { useParams } from 'react-router-dom';

const ProgressPage = () => {
  const { userId, courseId } = useParams();
  const [progressData, setProgressData] = useState(null);
  const [isLoading, setIsLoading] = useState(true);
  const [error, setError] = useState(null);

  useEffect(() => {
    const fetchProgressData = async () => {
      try {
        let url;
        if (courseId) {
          url = `/api/users/${userId}/progress/${courseId}`;
        } else {
          url = `/api/users/${userId}/progress`;
        }
        
        const response = await fetch(url);
        if (!response.ok) {
          throw new Error('Failed to fetch progress data');
        }
        const data = await response.json();
        setProgressData(data);
      } catch (err) {
        setError(err.message);
      } finally {
        setIsLoading(false);
      }
    };

    fetchProgressData();
  }, [userId, courseId]);

  if (isLoading) return <div>Loading progress data...</div>;
  if (error) return <div>Error: {error}</div>;
  if (!progressData) return <div>No progress data available</div>;

  return (
    <div className="progress-container">
      {courseId ? (
        // Course-specific progress view
        <div className="course-progress">
          <h1>Your Progress: {progressData.courseTitle}</h1>
          
          {progressData.completed ? (
            <div className="completion-banner">
              <h2>Congratulations! You Passed!</h2>
              <p>You've successfully completed the quiz and mastered the course material.</p>
            </div>
          ) : null}
          
          <div className="progress-details">
            <table>
              <tbody>
                <tr>
                  <td>Quiz Results</td>
                  <td>Questions - {progressData.totalQuestions}</td>
                </tr>
                <tr>
                  <td>Course - {progressData.courseTitle}</td>
                  <td>Correct Answers - {progressData.correctAnswers}</td>
                </tr>
                <tr>
                  <td colSpan="2">Score - {progressData.score}%</td>
                </tr>
              </tbody>
            </table>
          </div>
          
          {progressData.completed && (
            <div className="learning-progress">
              <h3>Your Learning Progress</h3>
              <div className="completed-course">
                <p>"{progressData.courseTitle}" has been marked as completed in your learning path.</p>
              </div>
            </div>
          )}
        </div>
      ) : (
        // Overall progress view
        <div className="overall-progress">
          <h1>Your Learning Progress</h1>
          
          <div className="progress-summary">
            <h2>Enrolled Courses</h2>
            <p>Total Courses: {progressData.totalCourses}</p>
            <p>Completed Courses: {progressData.completedCourses}</p>
            <p>Completion Rate: {progressData.completionRate}%</p>
          </div>
          
          <div className="courses-list">
            <h3>Course Progress Details</h3>
            <ul>
              {progressData.courses.map(course => (
                <li key={course.courseId} className={course.completed ? 'completed' : 'in-progress'}>
                  <h4>{course.courseTitle}</h4>
                  <p>Status: {course.completed ? 'Completed' : 'In Progress'}</p>
                  {course.score && <p>Score: {course.score}%</p>}
                  <p>Last Updated: {new Date(course.lastUpdated).toLocaleDateString()}</p>
                </li>
              ))}
            </ul>
          </div>
        </div>
      )}
    </div>
  );
};

export default ProgressPage;