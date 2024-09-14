import React from 'react';

function ProductList({ products, handleEditProduct, handleDeleteProduct }) {
    return (
        <ul>
            {products.map((product) => (
                <li key={product.id}>
                    ID: {product.id} - {product.name} - {product.description} - ${product.price} - Quantity: {product.quantity}
                    <button onClick={() => handleEditProduct(product)}>Edit</button>
                    <button onClick={() => handleDeleteProduct(product.id)}>Delete</button>
                </li>
            ))}
        </ul>
    );
}

export default ProductList;
