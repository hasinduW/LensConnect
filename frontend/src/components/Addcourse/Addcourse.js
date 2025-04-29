import React, { useState } from 'react';
import axios from 'axios';

function Addcourse() {
  const [course, setCourse] = useState({
    courseId: '',
    courseImage: null,
    courseName: '',
    courseCategory: '',
    courseDetails: ''
  });

  const { courseId, courseImage, courseName, courseCategory, courseDetails } = course;

  const onInputChange = (e) => {
    if (e.target.name === 'courseImage') {
      setCourse({ ...course, courseImage: e.target.files[0] });
    } else {
      setCourse({ ...course, [e.target.name]: e.target.value });
    }
  };

  const onsubmit = async (e) => {
    e.preventDefault();

    const formData = new FormData();
    formData.append("file", courseImage);
    let imageUrl = "";

    try {
      const response = await axios.post(
        "http://localhost:8080/course/CourseImage",
        formData,
        { headers: { 'Content-Type': 'multipart/form-data' } }
      );
      // Extract filename from returned full URL
      const fullUrl = response.data;
      imageUrl = fullUrl.split("/").pop(); // Get just the filename
    } catch (error) {
      console.error("Error uploading image: ", error);
      alert("Error uploading image");
      return;
    }

    const updatedCourse = {
      courseId,
      courseImage: imageUrl,
      courseName,
      courseCategory,
      courseDetails
    };

    try {
      await axios.post("http://localhost:8080/course/updateCourse", updatedCourse);
      alert("Course added successfully");
    } catch (error) {
      console.error("Error adding course: ", error);
      alert("Error adding course");
    }
  };

  return (
    <div>
      <p className='auth_topic'>Add course</p>
      <div className="form-container">
        <div className="form-sub-container">
          <form onSubmit={onsubmit}>
            <label>Course ID:</label><br />
            <input type="text" name="courseId" value={courseId} onChange={onInputChange} required /><br />

            <label>Course Image:</label><br />
            <input type="file" name="courseImage" accept="image/*" onChange={onInputChange} required /><br />

            <label>Course Name:</label><br />
            <input type="text" name="courseName" value={courseName} onChange={onInputChange} required /><br />

            <label>Course Category:</label><br />
            <select name="courseCategory" value={courseCategory} onChange={onInputChange} required>
              <option value="" disabled>Select Course Category</option>
              <option value="Fundamentals of Photography">Fundamentals of Photography</option>
              <option value="Portrait Photography">Portrait Photography</option>
              <option value="Landscape Photography">Landscape Photography</option>
              <option value="Product & Commercial Photography">Product & Commercial Photography</option>
              <option value="Event & Wedding Photography">Event & Wedding Photography</option>
              <option value="Street & Documentary Photography">Street & Documentary Photography</option>
              <option value="Studio Lighting & Flash Photography">Studio Lighting & Flash Photography</option>
              <option value="Photo Editing & Post-Processing">Photo Editing & Post-Processing</option>
              <option value="Drone & Aerial Photography">Drone & Aerial Photography</option>
              <option value="Creative & Fine Art Photography">Creative & Fine Art Photography</option>
            </select><br />

            <label>Course Details:</label><br />
            <textarea name="courseDetails" value={courseDetails} onChange={onInputChange} required></textarea><br /><br />

            <button type='submit' className='form-btn'>Submit</button>
          </form>
        </div>
      </div>
    </div>
  );
}

export default Addcourse;
