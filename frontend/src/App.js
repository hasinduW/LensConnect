import './App.css';
import React from 'react';
import { Route, Routes } from "react-router-dom";
import Home from "./components/Home/Home";
import Addcourse from "./components/Addcourse/Addcourse";
import DisplayCourse from "./components/DisplayCourse/DisplayCourse";
import UpdateCourse from "./components/UpdateCourse/UpdateCourse";
import QuizPage from './components/Quiz/Quiz';
import ProgressTrackingPage from './components/Quiz/ProgressTracking';

function App() {
  return (
    <Routes>
      <Route path="/" element={<Home />} />
      <Route path="/addcourse" element={<Addcourse />} />
      <Route path="/allcourses" element={<DisplayCourse />} />
      <Route path="/updateCourse/:id" element={<UpdateCourse />} />
      <Route path="/quiz" element={<QuizPage />} />
      <Route path="/progressTrackingPage" element={<ProgressTrackingPage />} />
      {/* <Route path="/course/:courseId/quiz/:quizId" element={<QuizPage />} />
        <Route path="/course/:courseId/results" element={<ProgressTrackingPage />} />
      </Routes> */}
    </Routes>
  );
}

export default App;

