import axios from "axios";
import { useState } from "react";
import { useNavigate } from 'react-router-dom';
import './css/Login.css';

const Login = ({ setIsAuthenticated }) => {
  const [username, setUsername] = useState('');
  const [password, setPassword] = useState('');
  const navigate = useNavigate();

  const handleLogin = async (e) => {
    e.preventDefault();
    try {
      await axios.post('http://localhost:8070/api/user/login', { username, password }, { withCredentials: true });
      setIsAuthenticated(true);
      navigate('/home');
    } catch (error) {
      console.error('Login failed', error);
    }
  };

  return (
    <div className="login-container">
      <div className="login-left">
        {/* <img src="./assets/images/bnr_logo.png" alt="Logo" className="logo" /> */}
        <h2>Welcome To,</h2>
        <h1>IB Back Office</h1>
      </div>
      <div className="login-right">
        <h2>Login</h2>
        <p>Please enter your credentials.</p>
        <form onSubmit={handleLogin}>
          <div className="form-group">
            <input
              type="text"
              placeholder="Username"
              value={username}
              onChange={(e) => setUsername(e.target.value)}
              required
            />
          </div>
          <div className="form-group">
            <input
              type="password"
              placeholder="Password"
              value={password}
              onChange={(e) => setPassword(e.target.value)}
              required
            />
          </div>
          <button type="submit" className="login-button">Login</button>
        </form>
      </div>
    </div>
  );
};

export default Login;