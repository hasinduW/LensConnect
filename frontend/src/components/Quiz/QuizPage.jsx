import React, { useState, useEffect } from 'react';
import { useParams } from 'react-router-dom';
import { getQuizQuestions, submitQuizAnswers } from './progressService.js';
import '../Quiz/ProgressTrackingPage.css';

const QuizPage = () => {
  const { courseId } = useParams();
  const [questions, setQuestions] = useState([]);
  const [currentQuestionIndex, setCurrentQuestionIndex] = useState(0);
  const [selectedAnswers, setSelectedAnswers] = useState({});
  const [quizSubmitted, setQuizSubmitted] = useState(false);
  const [score, setScore] = useState(null);

  useEffect(() => {
    const fetchQuestions = async () => {
      const data = await getQuizQuestions(courseId);
      setQuestions(data);
    };
    fetchQuestions();
  }, [courseId]);

  const handleAnswerSelect = (questionId, answerIndex) => {
    setSelectedAnswers({
      ...selectedAnswers,
      [questionId]: answerIndex
    });
  };

  const handleNext = () => {
    if (currentQuestionIndex < questions.length - 1) {
      setCurrentQuestionIndex(currentQuestionIndex + 1);
    }
  };

  const handlePrevious = () => {
    if (currentQuestionIndex > 0) {
      setCurrentQuestionIndex(currentQuestionIndex - 1);
    }
  };

  const handleSubmit = async () => {
    const response = await submitQuizAnswers(courseId, selectedAnswers);
    setScore(response.score);
    setQuizSubmitted(true);
  };

  if (quizSubmitted) {
    return (
      <div className="quiz-container">
        <h1>Congratulations! You Passed!</h1>
        <p>You've successfully completed the quiz and mastered the course material.</p>
        
        <div className="quiz-results">
          <h2>Quiz Results</h2>
          <table>
            <tbody>
              <tr>
                <td>Course - Fundamentals of Photography</td>
                <td>Questions - {questions.length}</td>
              </tr>
              <tr>
                <td>Correct Answers</td>
                <td>{Math.round((score / 100) * questions.length)}</td>
              </tr>
              <tr>
                <td>Score</td>
                <td>{score}%</td>
              </tr>
            </tbody>
          </table>
        </div>
        
        <div className="progress-section">
          <h3>Your Learning Progress</h3>
          <h4>Course Completed</h4>
          <p>"Fundamentals of Photography" has been marked as completed in your learning path.</p>
        </div>
      </div>
    );
  }

  if (questions.length === 0) return <div>Loading...</div>;

  const currentQuestion = questions[currentQuestionIndex];

  return (
    <div className="quiz-container">
      <h2>Course: {currentQuestion.courseName}</h2>
      <h3>Quiz Progress</h3>
      <p>{currentQuestionIndex + 1}/{questions.length}</p>
      
      <div className="question-container">
        <h3>Question {currentQuestionIndex + 1}</h3>
        <p>{currentQuestion.text}</p>
        
        <div className="options">
          {currentQuestion.options.map((option, index) => (
            <div key={index} className="option">
              <input
                type="radio"
                id={`option-${index}`}
                name="answer"
                checked={selectedAnswers[currentQuestion.id] === index}
                onChange={() => handleAnswerSelect(currentQuestion.id, index)}
              />
              <label htmlFor={`option-${index}`}>{option}</label>
            </div>
          ))}
        </div>
      </div>
      
      <div className="navigation-buttons">
        <button onClick={handlePrevious} disabled={currentQuestionIndex === 0}>
          ← Previous
        </button>
        {currentQuestionIndex < questions.length - 1 ? (
          <button onClick={handleNext}>Next →</button>
        ) : (
          <button onClick={handleSubmit}>Submit Quiz</button>
        )}
      </div>
    </div>
  );
};

export default QuizPage;