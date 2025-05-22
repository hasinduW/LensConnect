import React, { useState } from 'react';
import { BrowserRouter as Router, Route, Routes } from "react-router-dom";
import Home from "./components/Home/Home";
import Addcourse from "./components/Addcourse/Addcourse";
import DisplayCourse from "./components/DisplayCourse/DisplayCourse";
import UpdateCourse from "./components/UpdateCourse/UpdateCourse";
import Navbar from './components/Navbar';
import QuizPage from './components/Quiz/QuizPage';
import ProgressTrackingPage from './components/Quiz/ProgressTrackingPage';

function App() {
  const [userId] = useState(localStorage.getItem('userId') || '1');

  return (
    <Router>
      <div className="App">
        <Navbar />
        <Routes>
          <Route path="/" element={<Home />} />
          <Route path="/addcourse" element={<Addcourse />} />
          <Route path="/allcourses" element={<DisplayCourse />} />
          <Route path="/updateCourse/:id" element={<UpdateCourse />} />
          <Route path="/quiz/:courseId" element={<QuizPage userId={userId} />} />
          <Route path="/progress" element={<ProgressTrackingPage userId={userId} />} />
        </Routes>
      </div>
    </Router>
  );
}

export default App;

