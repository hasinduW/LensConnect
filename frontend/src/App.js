import React, { useState } from 'react';
import Quiz from './components/Quiz/Quiz';
import './App.css';

function App() {
  // In a real app, these would come from authentication or route params
  const [userId] = useState(1); // Mock user ID
  const [courseId, setCourseId] = useState(''); // For demo purposes
  const [startQuiz, setStartQuiz] = useState(false);

  const handleStartQuiz = (e) => {
    e.preventDefault();
    if (courseId) {
      setStartQuiz(true);
    }
  };

  if (startQuiz) {
    return (
      <div className="app-container">
        <Quiz courseId={courseId} userId={userId} />
      </div>
    );
  }

  return (
    <div className="app-container">
      <div className="quiz-start">
        <h1>Photographic Learning Platform</h1>
        <h2>Beginners Start Up</h2>
        
        <form onSubmit={handleStartQuiz}>
          <div className="form-group">
            <label htmlFor="courseId">Enter Course ID:</label>
            <input
              type="text"
              id="courseId"
              value={courseId}
              onChange={(e) => setCourseId(e.target.value)}
              placeholder="e.g: 123"
              required
            />
          </div>
          <button type="submit" className="start-button">
            Start Quiz
          </button>
        </form>
      </div>
    </div>
  );
}

export default App;