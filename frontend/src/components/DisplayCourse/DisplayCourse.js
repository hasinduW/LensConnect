import axios from 'axios';
import React, { useEffect, useState } from 'react';

function DisplayCourse() {
  const [courses, setCourses] = useState([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(null);

  useEffect(() => {
    loadCourses();
  }, []);

  const loadCourses = async () => {
    try {
      const result = await axios.get("http://localhost:8080/course/courses");
      setCourses(result.data);
      setLoading(false);
    } catch (error) {
      setError("Error fetching courses.");
      setLoading(false);
      console.error("Error fetching courses:", error);
    }
  };

  const handleUpdate = (id) => {
    window.location.href = `/updateCourse/${id}`;
  };

  const deleteCourse = async (id) => {
    const confirmationMessage = window.confirm("Are you sure you want to delete this course?");
  
    if (confirmationMessage) {
      try {
        const response = await axios.delete(`http://localhost:8080/course/course/${id}`);
  
        if (response.status === 200) {
          loadCourses();  // Reload course data after deletion
          alert("Course deleted successfully");
        } else {
          alert("Error deleting course: " + response.data);
        }
      } catch (error) {
        console.error("Delete error:", error);
  
        if (error.response) {
          alert(`Error deleting course: ${error.response.data || error.response.statusText}`);
        } else if (error.request) {
          alert("No response received from the server.");
        } else {
          alert("Error with request setup: " + error.message);
        }
      }
    }
  };

  if (loading) return <div>Loading courses...</div>;
  if (error) return <div>{error}</div>;

  return (
    <div>
      <h1>All Courses</h1>
      <table border="1" cellPadding="10">
        <thead>
          <tr>
            <th>Course ID</th>
            <th>Course Image</th>
            <th>Course Name</th>
            <th>Course Category</th>
            <th>Course Details</th>
            <th>Action</th>
          </tr>
        </thead>
        <tbody>
          {courses.map((course) => (
            <tr key={course.id}>
              <td>{course.courseId}</td>
              <td>
                <img
                  src={`http://localhost:8080/uploads/${course.courseImage}`}
                  alt={course.courseName}
                  style={{ width: "300px" }}
                />
              </td>
              <td>{course.courseName}</td>
              <td>{course.courseCategory}</td>
              <td>{course.courseDetails}</td>
              <td>
                <button onClick={() => handleUpdate(course.id)}>Update</button>
                <button onClick={() => deleteCourse(course.id)}>Delete</button>
              </td>
            </tr>
          ))}
        </tbody>
      </table>
    </div>
  );
}

export default DisplayCourse;
