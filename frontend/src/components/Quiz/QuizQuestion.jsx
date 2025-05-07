import React from 'react';

const QuizQuestion = ({ question, selectedAnswer, onAnswerSelect, onNext, isLastQuestion }) => {
    return (
        <div className="question-card">
            <h3>{question.text}</h3>
            <div className="options">
                {question.options.map(option => (
                    <div 
                        key={option.id} 
                        className={`option ${selectedAnswer === option.id ? 'selected' : ''}`}
                        onClick={() => onAnswerSelect(question.id, option.id)}
                    >
                        {option.text}
                    </div>
                ))}
            </div>
            <button 
                onClick={onNext}
                disabled={!selectedAnswer}
            >
                {isLastQuestion ? 'Finish Quiz' : 'Next Question'}
            </button>
        </div>
    );
};

export default QuizQuestion;