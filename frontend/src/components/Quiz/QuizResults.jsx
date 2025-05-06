import React from 'react';

const QuizResults = ({ score, totalQuestions, passingScore, courseId }) => {
    const passed = score >= passingScore;
    
    return (
        <div className={`results-container ${passed ? 'passed' : 'failed'}`}>
            <h2>{passed ? 'Congratulations!' : 'Quiz Completed'}</h2>
            <p>Your score: {score}%</p>
            <p>Correct answers: {Math.round(totalQuestions * score / 100)}/{totalQuestions}</p>
            
            {passed ? (
                <div className="success-message">
                    <p>You've successfully completed this course!</p>
                    <button onClick={() => window.location.href = `/course/${courseId}/certificate`}>
                        Download Certificate
                    </button>
                </div>
            ) : (
                <div className="retry-message">
                    <p>You need at least {passingScore}% to pass. Would you like to try again?</p>
                    <button onClick={() => window.location.reload()}>
                        Retry Quiz
                    </button>
                </div>
            )}
        </div>
    );
};

export default QuizResults;