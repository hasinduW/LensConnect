import React from 'react';

function Home() {
  return (
    <div>
      <button onClick={()=> (window.location.href='/addpost')}>Add Post</button>
      <button onClick={()=> (window.location.href='/allposts')}>all Posts</button>
    </div>
  );
}

export default Home;
