import React, { useEffect, useState } from 'react';
import axios from 'axios';
import { useParams } from 'react-router-dom';

function UpdateCourse() {
  const { id } = useParams();

  const [formData, setFormData] = useState({
    courseId: '',
    courseImage: null,
    courseName: '',
    courseCategory: '',
    courseDetails: '',
  });

  useEffect(() => {
    const fetchCourseData = async () => {
      try {
        const response = await axios.get(`http://localhost:8080/course/courses/${id}`);
        const courseData = response.data;
        setFormData({
          courseId: courseData.courseId || '',
          courseImage: null, // Keep null because this is a file input
          courseName: courseData.courseName || '',
          courseCategory: courseData.courseCategory || '',
          courseDetails: courseData.courseDetails || ''
        });
      } catch (err) {
        console.error('Error fetching data:', err);
      }
    };
    fetchCourseData();
  }, [id]);

  const onInputChange = (e) => {
    const { name, value, files } = e.target;
    setFormData({
      ...formData,
      [name]: files ? files[0] : value
    });
  };

  const onSubmit = async (e) => {
    e.preventDefault();
    const data = new FormData();

    data.append('itemDetails', JSON.stringify({
      courseId: formData.courseId,
      courseName: formData.courseName,
      courseCategory: formData.courseCategory,
      courseDetails: formData.courseDetails
    }));

    if (formData.courseImage) {
      data.append('file', formData.courseImage);
    }

    try {
      await axios.put(`http://localhost:8080/course/course/${id}`, data, {
        headers: {
          'Content-Type': 'multipart/form-data',
        },
      });
      alert("Course updated successfully");
      window.location.href = "/allcourses";
    } catch (err) {
      console.error('Error updating data:', err);
      alert("Error updating course");
    }
  };

  return (
    <div>
      <h1>Update Courses</h1>
      <div>
        <p className='auth_topic'>Edit course</p>
        <div className="form-container">
          <div className="form-sub-container">
            <form onSubmit={onSubmit}>
              <label>Course ID:</label><br />
              <input type="text" name="courseId" value={formData.courseId} onChange={onInputChange} required /><br />

              <label>Course Image:</label><br />
              <input type="file" name="courseImage" accept="image/*" onChange={onInputChange} /><br />

              <label>Course Name:</label><br />
              <input type="text" name="courseName" value={formData.courseName} onChange={onInputChange} required /><br />

              <label>Course Category:</label><br />
              <select name="courseCategory" value={formData.courseCategory} onChange={onInputChange} required>
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
              <textarea name="courseDetails" value={formData.courseDetails} onChange={onInputChange} required></textarea><br /><br />

              <button type="submit" className="form-btn">Update Course</button>
            </form>
          </div>
        </div>
      </div>
    </div>
  );
}

export default UpdateCourse;
