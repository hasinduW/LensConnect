import React, { useState, useEffect } from 'react';
import { useParams, useNavigate } from 'react-router-dom';

const QuizPage = () => {
  const { courseId } = useParams();
  const navigate = useNavigate();
  const [currentQuestionIndex, setCurrentQuestionIndex] = useState(0);
  const [selectedAnswer, setSelectedAnswer] = useState(null);
  const [quizData, setQuizData] = useState(null);
  const [userAnswers, setUserAnswers] = useState([]);
  const [isLoading, setIsLoading] = useState(true);
  const [error, setError] = useState(null);

  useEffect(() => {
    const fetchQuizData = async () => {
      try {
        const response = await fetch(`/api/courses/${courseId}/quiz`);
        if (!response.ok) {
          throw new Error('Failed to fetch quiz data');
        }
        const data = await response.json();
        setQuizData(data);
        setUserAnswers(new Array(data.questions.length).fill(null));
      } catch (err) {
        setError(err.message);
      } finally {
        setIsLoading(false);
      }
    };

    fetchQuizData();
  }, [courseId]);

  const handleAnswerSelect = (answerIndex) => {
    setSelectedAnswer(answerIndex);
    const updatedAnswers = [...userAnswers];
    updatedAnswers[currentQuestionIndex] = answerIndex;
    setUserAnswers(updatedAnswers);
  };

  const handleNextQuestion = () => {
    if (currentQuestionIndex < quizData.questions.length - 1) {
      setCurrentQuestionIndex(currentQuestionIndex + 1);
      setSelectedAnswer(userAnswers[currentQuestionIndex + 1]);
    }
  };

  const handlePreviousQuestion = () => {
    if (currentQuestionIndex > 0) {
      setCurrentQuestionIndex(currentQuestionIndex - 1);
      setSelectedAnswer(userAnswers[currentQuestionIndex - 1]);
    }
  };

  const handleSubmitQuiz = async () => {
    try {
      const response = await fetch('/api/progress', {
        method: 'POST',
        headers: {
          'Content-Type': 'application/json',
        },
        body: JSON.stringify({
          courseId,
          answers: userAnswers,
        }),
      });

      if (!response.ok) {
        throw new Error('Failed to submit quiz');
      }

      navigate(`/progress/${courseId}/result`);
    } catch (err) {
      setError(err.message);
    }
  };

  if (isLoading) return <div>Loading quiz...</div>;
  if (error) return <div>Error: {error}</div>;
  if (!quizData) return <div>No quiz data available</div>;

  const currentQuestion = quizData.questions[currentQuestionIndex];

  return (
    <div className="quiz-container">
      <h1>{quizData.courseTitle} - Quiz</h1>
      <div className="progress-indicator">
        Question {currentQuestionIndex + 1}/{quizData.questions.length}
      </div>
      
      <div className="question-card">
        <h3>Question {currentQuestionIndex + 1}</h3>
        <p>{currentQuestion.questionText}</p>
        
        <div className="options-list">
          {currentQuestion.options.map((option, index) => (
            <div 
              key={index} 
              className={`option ${selectedAnswer === index ? 'selected' : ''}`}
              onClick={() => handleAnswerSelect(index)}
            >
              <input 
                type="radio" 
                name="quiz-option" 
                checked={selectedAnswer === index}
                onChange={() => handleAnswerSelect(index)}
              />
              {option}
            </div>
          ))}
        </div>
      </div>
      
      <div className="navigation-buttons">
        <button 
          onClick={handlePreviousQuestion}
          disabled={currentQuestionIndex === 0}
        >
          ← Previous
        </button>
        
        {currentQuestionIndex < quizData.questions.length - 1 ? (
          <button 
            onClick={handleNextQuestion}
            disabled={selectedAnswer === null}
          >
            Next →
          </button>
        ) : (
          <button 
            onClick={handleSubmitQuiz}
            disabled={selectedAnswer === null}
            className="submit-button"
          >
            Submit Quiz
          </button>
        )}
      </div>
    </div>
  );
};

export default QuizPage;