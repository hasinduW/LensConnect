const API_BASE_URL = 'http://localhost:8080/api';

export const getQuizQuestions = async (courseId) => {
  const response = await fetch(`${API_BASE_URL}/courses/${courseId}/quiz`);
  if (!response.ok) throw new Error('Failed to fetch quiz questions');
  return await response.json();
};

export const submitQuizAnswers = async (courseId, answers) => {
  const userId = localStorage.getItem('userId') || '1';
  const response = await fetch(`${API_BASE_URL}/progress`, {
    method: 'POST',
    headers: {
      'Content-Type': 'application/json',
    },
    body: JSON.stringify({
      userId,
      courseId,
      answers
    }),
  });
  if (!response.ok) throw new Error('Failed to submit quiz');
  return await response.json();
};

export const getOverallProgress = async (userId) => {
  const response = await fetch(`${API_BASE_URL}/users/${userId}/progress`);
  if (!response.ok) throw new Error('Failed to fetch overall progress');
  return await response.json();
};

export const getCourseProgress = async (userId, courseId) => {
  const response = await fetch(`${API_BASE_URL}/users/${userId}/progress/${courseId}`);
  if (!response.ok) throw new Error('Failed to fetch course progress');
  return await response.json();
};