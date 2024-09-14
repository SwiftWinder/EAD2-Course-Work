import axios from 'axios';

const BASE_URL = 'http://localhost:8081/users';

export const loginUser = async (email, password) => {
    try {
        const response = await axios.post(`${BASE_URL}/login`, { email, password });
        return response.data;
    } catch (error) {
        console.error('Error logging in:', error);
        throw error;
    }
};

export const registerUser = async (user) => {
    try {
        await axios.post(BASE_URL, user);
    } catch (error) {
        console.error('Error registering user:', error);
        throw error;
    }
};
