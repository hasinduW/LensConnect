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

  const handleDelete = async (courseId) => {
    try {
      await axios.delete(`http://localhost:8080/course/${courseId}`);
      setCourses(courses.filter(course => course.courseId !== courseId));
    } catch (error) {
      console.error("Error deleting course:", error);
    }
  };

  const handleEdit = (courseId) => {
    console.log(`Edit course with ID: ${courseId}`);
    // Optional: redirect or popup logic here
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
          {courses.map((course, index) => (
            <tr key={index}>
              <td>{course.courseId}</td>
              <td>
                <img
                  src={`http://localhost:8080/${course.courseImage}`}
                  alt={course.courseName}
                  style={{ width: "300px" }}
                />
              </td>
              <td>{course.courseName}</td>
              <td>{course.courseCategory}</td>
              <td>{course.courseDetails}</td>
              <td>
                <button onClick={() => handleEdit(course.courseId)}>Edit</button>
                <button onClick={() => handleDelete(course.courseId)}>Delete</button>
              </td>
            </tr>
          ))}
        </tbody>
      </table>
    </div>
  );
}

export default DisplayCourse;
