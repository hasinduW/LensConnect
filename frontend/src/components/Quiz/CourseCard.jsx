import React from 'react';
import './ProgressTrackingPage.css';

const CourseCard = ({ course, onClick, isSelected }) => {
  return (
    <div 
      className={`course-card ${isSelected ? 'selected' : ''}`}
      onClick={onClick}
    >
      <h3>{course.name}</h3>
      <p>{course.isCompleted ? 'Completed' : 'In Progress'}</p>
      <div className="progress-bar">
        <div 
          className="progress-fill"
          style={{ width: `${course.completionPercentage}%` }}
        ></div>
      </div>
      <p>{Math.round(course.completionPercentage)}% Complete</p>
    </div>
  );
};

export default CourseCard;