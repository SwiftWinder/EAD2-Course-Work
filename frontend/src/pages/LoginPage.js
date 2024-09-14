
import React, { useState, useContext } from 'react';
import axios from 'axios';
import { useNavigate } from 'react-router-dom'; // Import useNavigate
import { UserContext } from '../contexts/UserContext';

function LoginPage() {
    const [email, setEmail] = useState('');
    const [password, setPassword] = useState('');
    const [error, setError] = useState('');
    const { setUser } = useContext(UserContext);
    const navigate = useNavigate(); // Initialize useNavigate

    const handleLogin = async () => {
        try {
            const response = await axios.post('http://localhost:8081/users/login', { email, password });
            setUser(response.data.user); // Assuming the API returns the user data
            alert('Login Successful');
            navigate('/'); // Redirect to the home page after login
        } catch (err) {
            setError('Invalid email or password');
        }
    };

    return (
        <div>
            <h2>Login</h2>
            <input
                type="email"
                placeholder="Email"
                value={email}
                onChange={(e) => setEmail(e.target.value)}
            />
            <input
                type="password"
                placeholder="Password"
                value={password}
                onChange={(e) => setPassword(e.target.value)}
            />
            <button onClick={handleLogin}>Login</button>
            {error && <p>{error}</p>}
        </div>
    );
}

export default LoginPage;
