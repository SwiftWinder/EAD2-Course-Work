import React, { useState, useEffect } from 'react';
import { fetchProducts, addOrUpdateProduct, deleteProduct } from '../services/productService';
import ProductForm from '../components/ProductForm';
import ProductList from '../components/ProductList';

function ProductPage() {
    const [products, setProducts] = useState([]);
    const [name, setName] = useState('');
    const [description, setDescription] = useState('');
    const [price, setPrice] = useState('');
    const [quantity, setQuantity] = useState('');
    const [editProductId, setEditProductId] = useState(null);

    useEffect(() => {
        loadProducts();
    }, []);

    const loadProducts = async () => {
        const data = await fetchProducts();
        setProducts(data);
    };

    const handleAddOrUpdateProduct = async () => {
        await addOrUpdateProduct({ name, description, price, quantity }, editProductId);
        loadProducts();
        setName('');
        setDescription('');
        setPrice('');
        setQuantity('');
        setEditProductId(null);
    };

    const handleEditProduct = (product) => {
        setName(product.name);
        setDescription(product.description);
        setPrice(product.price);
        setQuantity(product.quantity);
        setEditProductId(product.id);
    };

    const handleDeleteProduct = async (id) => {
        await deleteProduct(id);
        loadProducts();
    };

    return (
        <div>
            <h1>Product Management</h1>
            <ProductForm
                name={name}
                description={description}
                price={price}
                quantity={quantity}
                setName={setName}
                setDescription={setDescription}
                setPrice={setPrice}
                setQuantity={setQuantity}
                handleAddOrUpdateProduct={handleAddOrUpdateProduct}
                editProductId={editProductId}
            />
            <h2>Products List</h2>
            <ProductList
                products={products}
                handleEditProduct={handleEditProduct}
                handleDeleteProduct={handleDeleteProduct}
            />
        </div>
    );
}

export default ProductPage;
