import axios from 'axios';

const BASE_URL = 'http://localhost:8080/products';

export const fetchProducts = async () => {
    try {
        const response = await axios.get(BASE_URL);
        return response.data;
    } catch (error) {
        console.error('Error fetching products:', error);
        throw error;
    }
};

export const addOrUpdateProduct = async (product, productId) => {
    try {
        if (productId) {
            await axios.put(`${BASE_URL}/${productId}`, product);
        } else {
            await axios.post(BASE_URL, product);
        }
    } catch (error) {
        console.error('Error adding or updating product:', error);
        throw error;
    }
};

export const deleteProduct = async (id) => {
    try {
        await axios.delete(`${BASE_URL}/${id}`);
    } catch (error) {
        console.error('Error deleting product:', error);
        throw error;
    }
};
