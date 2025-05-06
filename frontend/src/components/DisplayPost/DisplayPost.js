import React, { useEffect, useState } from 'react'
import axios from 'axios'
import { useParams } from 'react-router'
import AddPost from '../AddPost/AddPost';

//display function

function DisplayPost() {
  const [posts, setPosts] = useState([]);
  const { id } = useParams();

  useEffect(()=> { 
    loadPosts();

  },[])

const loadPosts = async () =>  {
  const result = await axios.get("http://localhost:8080/posts")
  setPosts(result.data);
}

const UpdateNavigate = (id)=>{
  window.location.href = `/updatePost/${id}`;
}

  return (
    <div className="feed-container">
     {posts.map((post, index) => (
      <div className="post-card" key={index}>
        {/* Profile section */}
        <div className="post-header">
          <div className="profile-section">
            <div className="avatar-container">
              <img 
                src={`https://via.placeholder.com/40`} 
                alt="Profile" 
                className="avatar"
              />
            </div>
            {/*<div className="username">{post.userName || "User"}</div>*/}
            <div className="badge">{post.postCategory || "category"}</div>
          </div>
          <div className="post-actions">
            <button className="action-btn delete-btn">🗑️</button>
            <button onClick={()=> UpdateNavigate(post.id)} className="action-btn edit-btn">✏️</button>
          </div>
        </div>
        
        {/* Content section */}
        <div className="post-content">
          <h3 className="post-title">{post.postTitle}</h3>
          <div className="post-body">
            {post.postImage && (
              <div className="post-image-container">
                <img 
                  src={`http://localhost:8080/uploads/${post.postImage}`} 
                  alt={post.postTitle} 
                  className="post-image"
                />
              </div>
            )}
            <p className="post-description">{post.postDescription}</p>
          </div>
        </div>
        
        {/* Interaction buttons */}
        <div className="post-footer">
          <button className="interaction-btn like-btn">
            <span>👍</span>
          </button>
          <button className="interaction-btn comment-btn">
            <span>💬</span>
          </button>
          <button className="interaction-btn share-btn">
            <span>↗️</span>
          </button>
        </div>
      </div>
    ))}
  </div>
);
}

export default DisplayPost;
