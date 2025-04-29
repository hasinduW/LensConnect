import React from "react";
import { Route, Routes } from "react-router-dom";
import Home from "./components/Home/Home";
import Addcourse from "./components/Addcourse/Addcourse";
import DisplayCourse from "./components/DisplayCourse/DisplayCourse";

function App() {
  return (
    <Routes>
      <Route path="/" element={<Home />} />
      <Route path="/addcourse" element={<Addcourse />} />
      <Route path="/allcourses" element={<DisplayCourse />} />
    </Routes>
  );
}

export default App;
