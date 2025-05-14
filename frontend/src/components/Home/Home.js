import React from 'react';
import { useNavigate } from 'react-router-dom';

function Home() {
  const navigate = useNavigate();

  return (
    <div>
      <button onClick={() => navigate('/addcourse')}>Add Course</button>
      <button onClick={() => navigate('/allcourses')}>All Courses</button>
    </div>
  );
}

export default Home;
