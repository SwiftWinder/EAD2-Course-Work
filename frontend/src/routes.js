import React from 'react';
import { BrowserRouter as Router, Route, Routes } from 'react-router-dom';
import NavBar from './components/NavBar';
import ProductPage from './pages/ProductPage';
import LoginPage from './pages/LoginPage';
import RegisterPage from './pages/RegisterPage';
import CategoryPage from './pages/CategoryPage';


function AppRouter() {
    return (
        <Router>
            <NavBar />
            <Routes>
                <Route path="/" element={<ProductPage />} />
                <Route path="/login" element={<LoginPage />} />
                <Route path="/register" element={<RegisterPage />} />
                <Route path="/products" element={<ProductPage />} />
                <Route path="/categories" element={<CategoryPage />} />
            </Routes>
        </Router>
    );
}

export default AppRouter;
