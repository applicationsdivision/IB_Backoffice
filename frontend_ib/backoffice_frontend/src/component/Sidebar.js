import React from 'react';
import { Link } from 'react-router-dom';
import './css/Sidebar.css';

const Sidebar = () => {
  return (
    <div className="sidebar">
      <ul>
        <li><Link to="/home">Home</Link></li>
        <li><Link to="/users">Users</Link></li>
      </ul>
    </div>
  );
};

export default Sidebar;
