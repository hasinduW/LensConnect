import React, {useEffect, useState } from 'react'; // removed unused useEffect
import { useNavigate } from 'react-router-dom';
import axios from 'axios';

function AddPost() {
  const navigate= useNavigate();
  const [posts, setPosts] = useState({
    postId: '',
    postImage: '',
    postTitle: '',
    postDescription: '',
    postCategory:''
  });

  const { postId,postTitle, postDescription, postCategory} = posts;

  const onInputChange = (e) => {
    if (e.target.name === 'postImage') {
      setPosts({ ...posts, postImage: e.target.files[0] });
    } else {
      setPosts({ ...posts, [e.target.name]: e.target.value });
    }
  };

  const onsubmit = async (e) => {
    e.preventDefault();

    const formData = new FormData();
    
    formData.append("file", posts.postImage);
    let imageName = "";

    try {
      const response = await axios.post(`http://localhost:8080/posts/postImg`, formData, {
        headers: {
          'Content-Type': 'multipart/form-data'
        },
      });
      imageName = response.data;
    } catch (error) {
      alert("Error uploading image");
      return;
    }

    const updatePosts = { ...posts, postImage: imageName };

    try {
      await axios.post(`http://localhost:8080/posts`, updatePosts);
      alert("Post added successfully");
      window.location.reload();
    } catch (err) {
      alert("Error saving post data");
    }
  };

  return (
    <div>
      <p className='auth_topic'>Add Post</p>
      <div className="form-container">
        <div className="form-sub-container">
          <form id="postForm" onSubmit={(e) => onsubmit(e)}>
            <label htmlFor="postId">Post ID:</label><br />
            <input type="text" id="postId" name="postId" required onChange={(e) => onInputChange(e)} value={postId} /><br />
            
            <label htmlFor="postImage">Post Image:</label><br />
            <input
              type="file"
              id="postImage"
              name="postImage"
              accept="image/*"
              onChange={(e) => onInputChange(e)}
            /><br />
            
            <label htmlFor="postTitle">Title:</label><br />
            <input 
              type="text" 
              id="postTitle" 
              name="postTitle" 
              required 
              onChange={(e) => onInputChange(e)} 
              value={postTitle} 
              placeholder="(e.g., Sunset at Golden Beach)"
            /><br />
            
            <label htmlFor="postDescription">Post Description:</label><br />
            <textarea 
              id="postDescription" 
              name="postDescription" 
              onChange={(e) => onInputChange(e)} 
              value={postDescription}
              placeholder="Share your tips/story..."
            ></textarea><br />
            
            <label htmlFor="postCategory">Category:</label><br />
            <select
              id="postCategory"
              name="postCategory"
              value={postCategory}
              onChange={(e) => onInputChange(e)}
              required
            >
              <option value="" disabled>Select</option>
              <option value="travel">WildLife</option>
              <option value="food">Food</option>
              <option value="adventure">Adventure</option>
              <option value="lifestyle">Lifestyle</option>
              
            </select><br /><br />
            
            <button type='submit' className='form-btn'>Add Post</button>
          </form>
        </div>
      </div>
    </div>
  );

}

export default AddPost;
 