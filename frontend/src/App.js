
import React from "react";
import { Route , Routes } from "react-router-dom";
import Home from "./components/Home/Home";
import AddPost from "./components/AddPost/AddPost";
import DisplayPost from "./components/DisplayPost/DisplayPost";
import UpdatePost from "./components/UpdatePost/UpdatePost";

function App() {
  return (
    <div>
      <React.Fragment>
        <Routes>
          < Route path="/" element={<Home/>} />
          <Route path="/addpost" element={<AddPost/>} />
          <Route path="/allposts" element={<DisplayPost/>} />
          <Route path="/updatePost/:id" element={<UpdatePost/>} />
        </Routes>
      </React.Fragment>
    </div>

  );
}

export default App;

