import React from 'react';

function ProductForm({ name, description, price, quantity, setName, setDescription, setPrice, setQuantity, handleAddOrUpdateProduct, editProductId }) {
    return (
        <div>
            <input
                type="text"
                placeholder="Name"
                value={name}
                onChange={(e) => setName(e.target.value)}
            />
            <input
                type="text"
                placeholder="Description"
                value={description}
                onChange={(e) => setDescription(e.target.value)}
            />
            <input
                type="number"
                placeholder="Price"
                value={price}
                onChange={(e) => setPrice(e.target.value)}
            />
            <input
                type="number"
                placeholder="Quantity"
                value={quantity}
                onChange={(e) => setQuantity(e.target.value)}
            />
            <button onClick={handleAddOrUpdateProduct}>
                {editProductId ? 'Update Product' : 'Add Product'}
            </button>
        </div>
    );
}

export default ProductForm;
