import React, { useState, useEffect } from 'react';
import axios from 'axios';
import QuizQuestion from './QuizQuestion';
import QuizResults from './QuizResults';
import './styles.css';

const Quiz = ({ courseId, userId }) => {
    const [questions, setQuestions] = useState([]);
    const [currentQuestionIndex, setCurrentQuestionIndex] = useState(0);
    const [score, setScore] = useState(0);
    const [quizCompleted, setQuizCompleted] = useState(false);
    const [userAnswers, setUserAnswers] = useState({});
    const [loading, setLoading] = useState(true);
    const [error, setError] = useState(null);
    
    useEffect(() => {
        setLoading(true);
        
        // Mock questions data
        const mockQuestions = [
            {
                id: 1,
                text: "What is the correct aperture for portrait photography?",
                options: [
                    { id: 1, text: "f/1.4" },
                    { id: 2, text: "f/8" },
                    { id: 3, text: "f/16" }
                ],
                correctAnswer: 1
            },
            {
                id: 2,
                text: "Which ISO is best for low light conditions?",
                options: [
                    { id: 4, text: "ISO 100" },
                    { id: 5, text: "ISO 400" },
                    { id: 6, text: "ISO 3200" }
                ],
                correctAnswer: 6
            }
        ];
        
        // Simulate API call with timeout
        setTimeout(() => {
            setQuestions(mockQuestions);
            setLoading(false);
        }, 1000); // Simulate network delay

        // Original API call (commented out for now)
        /*
        const fetchQuestions = async () => {
            try {
                const response = await axios.get(`http://localhost:8080/api/quizzes/course/${courseId}`);
                setQuestions(response.data);
                setLoading(false);
            } catch (err) {
                console.error("Error fetching questions:", err);
                setError(err.message);
                setLoading(false);
            }
        };
        fetchQuestions();
        */
    }, [courseId]);
    
    const handleAnswerSelect = (questionId, answerId) => {
        setUserAnswers(prev => ({ ...prev, [questionId]: answerId }));
    };
    
    const handleNextQuestion = () => {
        if (currentQuestionIndex < questions.length - 1) {
            setCurrentQuestionIndex(currentQuestionIndex + 1);
        } else {
            calculateScore();
            setQuizCompleted(true);
        }
    };
    
    const calculateScore = () => {
        let correctAnswers = 0;
        questions.forEach(question => {
            const selectedAnswer = userAnswers[question.id];
            if (selectedAnswer && question.correctAnswer === selectedAnswer) {
                correctAnswers++;
            }
        });
        const calculatedScore = Math.round((correctAnswers / questions.length) * 100);
        setScore(calculatedScore);
        updateProgress(correctAnswers >= questions.length * 0.7, calculatedScore);
    };
    
    const updateProgress = async (passed, calculatedScore) => {
        try {
            // For demo purposes, we'll just log this instead of making an actual API call
            console.log('Progress would be updated:', {
                userId,
                courseId,
                status: passed ? 'COMPLETED' : 'IN_PROGRESS',
                quizScore: calculatedScore
            });
            
            /*
            // Original API call (commented out for now)
            await axios.put(`http://localhost:8080/api/progress`, {
                userId,
                courseId,
                status: passed ? 'COMPLETED' : 'IN_PROGRESS',
                quizScore: calculatedScore
            });
            */
        } catch (err) {
            console.error("Error updating progress:", err);
            setError("Failed to save your progress. Please try again.");
        }
    };
    
    if (loading) {
        return (
            <div className="loading-container">
                <p>Loading quiz questions...</p>
                <div className="spinner"></div>
            </div>
        );
    }
    
    if (error) {
        return (
            <div className="error-container">
                <p>Error: {error}</p>
                <button onClick={() => window.location.reload()}>Retry</button>
            </div>
        );
    }
    
    if (questions.length === 0) {
        return (
            <div className="no-questions">
                <p>No questions available for this quiz.</p>
            </div>
        );
    }
    
    return (
        <div className="quiz-container">
            {!quizCompleted ? (
                <QuizQuestion
                    question={questions[currentQuestionIndex]}
                    selectedAnswer={userAnswers[questions[currentQuestionIndex].id]}
                    onAnswerSelect={handleAnswerSelect}
                    onNext={handleNextQuestion}
                    isLastQuestion={currentQuestionIndex === questions.length - 1}
                />
            ) : (
                <QuizResults 
                    score={score}
                    totalQuestions={questions.length}
                    passingScore={70}
                    courseId={courseId}
                />
            )}
        </div>
    );
};

export default Quiz;