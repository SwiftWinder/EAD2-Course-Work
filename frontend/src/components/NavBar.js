/*
import React, { useContext, useState, useEffect } from 'react';
import { Link } from 'react-router-dom';
import { UserContext } from '../contexts/UserContext';

function NavBar() {
    const { user, setUser } = useContext(UserContext);
    const [showDropdown, setShowDropdown] = useState(false);

    useEffect(() => {
        console.log('NavBar rendered', user);  // Log user state on render
    }, [user]);

    const handleLogout = () => {
        setUser(null);
        alert('Logged out successfully');
    };

    return (
        <nav>
            <ul>
                <li><Link to="/">Home</Link></li>
                {user ? (
                    <>
                        <li onClick={() => setShowDropdown(!showDropdown)}>{user.username}</li>
                        {showDropdown && (
                            <ul>
                                <li onClick={handleLogout}>Logout</li>
                            </ul>
                        )}
                    </>
                ) : (
                    <>
                        <li><Link to="/login">Login</Link></li>
                        <li><Link to="/register">Register</Link></li>
                    </>
                )}
                <li><Link to="/products">Products</Link></li>
            </ul>
        </nav>
    );
}

export default NavBar;
*/

import React, { useContext, useState } from 'react';
import { Link } from 'react-router-dom';
import { UserContext } from '../contexts/UserContext';

function NavBar() {
    const { user, setUser } = useContext(UserContext);
    const [showDropdown, setShowDropdown] = useState(false);

    const handleLogout = () => {
        setUser(null);
        alert('Logged out successfully');
    };

    return (
        <nav>
            <ul>
                <li><Link to="/">Home</Link></li>
                <li><Link to="/categories">Categories</Link></li>  {/* Link to Category Page */}
                {user ? (
                    <>
                        <li onClick={() => setShowDropdown(!showDropdown)}>{user.username}</li>
                        {showDropdown && (
                            <ul>
                                <li onClick={handleLogout}>Logout</li>
                            </ul>
                        )}
                    </>
                ) : (
                    <>
                        <li><Link to="/login">Login</Link></li>
                        <li><Link to="/register">Register</Link></li>
                    </>
                )}
                <li><Link to="/products">Products</Link></li>
            </ul>
        </nav>
    );
}

export default NavBar;
