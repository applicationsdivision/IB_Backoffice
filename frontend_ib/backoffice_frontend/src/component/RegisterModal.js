import React, { useState } from 'react';
import axios from 'axios';
import './css/RegisterModal.css';

const RegisterModal = ({ onClose }) => {
    const [username, setUsername] = useState('');
    const [email, setEmail] = useState('');

    const handleRegister = async (e) => {
        e.preventDefault();
        try {
            await axios.post('http://localhost:8070/api/user/register', { username, email });
            onClose();
        } catch (error) {
            console.error('Registration failed:', error);
        }
    };

    return (
        <div className="modal">
            <div className="modal-content">
                <span className="close" onClick={onClose}>&times;</span>
                <h2>Register</h2>
                <form onSubmit={handleRegister}>
                    <div>
                        <label>Username:</label>
                        <input type="text" value={username} onChange={(e) => setUsername(e.target.value)} />
                    </div>
                    <div>
                        <label>Email:</label>
                        <input type="email" value={email} onChange={(e) => setEmail(e.target.value)} />
                    </div>
                    <button type="submit">Register</button>
                </form>
            </div>
        </div>
    );
};

export default RegisterModal;
