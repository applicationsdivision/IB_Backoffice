import React from 'react';
import Header from './Header';
import Sidebar from './Sidebar';

const Home = () => {
  return (
    <div>
       <Sidebar/>
      <Header/>
     
      <h2>Home Page</h2>
      <p>Welcome to the home page!</p>
    </div>
  );
};

export default Home;
