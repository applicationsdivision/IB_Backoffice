import './App.css';
import React, { useState, useEffect } from 'react';
import { BrowserRouter as Router, Routes, Route, Navigate } from 'react-router-dom';
import axios from 'axios';
import Login from './component/Login';
import Home from './component/Home';
import Users from './component/Users';
import RegisterModal from './component/RegisterModal';

function App() {
  const [isAuthenticated, setIsAuthenticated] = useState(false);

  useEffect(() => {
    axios.get('http://localhost:8070/api/user/check-session', { withCredentials: true })
      .then(response => {
        if (response.data.isAuthenticated) {
          setIsAuthenticated(true);
        }
      })
      .catch(error => {
        console.error('Session check failed:', error);
      });
  }, []);

  const handleLogout = async () => {
    try {
      await axios.get('http://localhost:8070/api/auth/logout', { withCredentials: true });
      setIsAuthenticated(false);
    } catch (error) {
      console.error('Logout failed:', error);
    }
  };

  return (
    <Router>
      <Routes>
        <Route
          path="/"
          element={isAuthenticated ? <Navigate to="/home" /> : <Login setIsAuthenticated={setIsAuthenticated} />}
        />
        <Route
          path="/login"
          element={isAuthenticated ? <Navigate to="/home" /> : <Login setIsAuthenticated={setIsAuthenticated} />}
        />
        <Route
          path="/register"
          element={isAuthenticated ? <Navigate to="/home" /> : <RegisterModal />}
        />
        <Route
          path="/home"
          element={isAuthenticated ? <Home onLogout={handleLogout} /> : <Navigate to="/login" />}
        />
        <Route
          path="/users"
          element={isAuthenticated ? <Users /> : <Navigate to="/login" />}
        />
      </Routes>
    </Router>
  );
}

export default App;
