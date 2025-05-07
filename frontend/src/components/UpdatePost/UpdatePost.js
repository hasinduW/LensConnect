import React, {useEffect, useState } from 'react'; 
import axios from 'axios';
import { useParams } from 'react-router';

function UpdatePost() {
    const { id } =useParams();
    const [fromData, setFromData] =useState({
    postId: '',
    postTitle: '',
    postDescription: '',
    postCategory:'',
    postImage: null,
    });
    useEffect(()=>{
        const fetchPostData =async() => {
            try{
                const response = await axios.get(`http://localhost:8080/posts/${id}`);
                const postData = response.data;
                setFromData({
                    postId: postData.postId || '',
                    postTitle: postData.postTitle || '',
                    postDescription: postData.postDescription || '',
                    postCategory: postData.postCategory || '',
                    postImage: null
                });
            }catch (err){
                console.error('error fetching data : ', err);
            }   
        };
        fetchPostData();
    }, [id]);

    const onInputChange = (e) => {
        const {name,value,files} = e.target;
        setFromData({
            ...fromData,
            [name]: files ? files[0] : value,
        })
    };

    const onsubmit =async (e) => {
        e.preventDefault();
        const data =new FormData();
        data.append('postDetails',JSON.stringify({
            postId:fromData.postId,
            postTitle:fromData.postTitle,
            postDescription:fromData.postDescription,
            postCategory:fromData.postCategory,
        }));

        if (fromData.postImage){
            data.append('file', fromData.postImage);
        }
        try{
            const response =await axios.put(`http://localhost/posts/${id}`, data);
            alert("Item Updated")
            window.location.href ="/allposts";

        }catch(err){
            console.error('error updating data : ', err);
            alert("Error updating item");
        }

    };
    
  return (
    <div>
      <h1>Update Post</h1>
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
              <option value="landscape">Landscape</option>
              <option value="adventure">Adventure</option>
              <option value="lifestyle">Lifestyle</option>
              
              
            </select><br /><br />
            
            <button type='submit' className='form-btn'>Add Post</button>
          </form>
        </div>
      </div>
    </div>
    </div>
  );
}

export default UpdatePost;
