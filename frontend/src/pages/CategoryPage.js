import React, { useState, useEffect } from "react";
import {
  getCategories,
  createCategory,
  updateCategory,
  deleteCategory,
} from "../services/categoryService";

function CategoryPage() {
  const [categories, setCategories] = useState([]);
  const [name, setName] = useState("");
  const [description, setDescription] = useState("");
  const [editCategoryId, setEditCategoryId] = useState(null);

  useEffect(() => {
    fetchCategories();
  }, []);

  const fetchCategories = async () => {
    const data = await getCategories();
    setCategories(data);
  };

  const handleAddOrUpdateCategory = async () => {
    const category = { name, description };
    if (editCategoryId) {
      await updateCategory(editCategoryId, category);
      setEditCategoryId(null);
    } else {
      await createCategory(category);
    }
    fetchCategories();
    setName("");
    setDescription("");
  };

  const handleEditCategory = (category) => {
    setName(category.name);
    setDescription(category.description);
    setEditCategoryId(category.id);
  };

  const handleDeleteCategory = async (id) => {
    await deleteCategory(id);
    fetchCategories();
  };

  return (
    <div className="CategoryPage">
      <h1>Category Management</h1>
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
        <button onClick={handleAddOrUpdateCategory}>
          {editCategoryId ? "Update Category" : "Add Category"}
        </button>
      </div>
      <h2>Categories List</h2>
      <ul>
        {categories.map((category) => (
          <li key={category.id}>
            {category.name} - {category.description}
            <button onClick={() => handleEditCategory(category)}>Edit</button>
            <button onClick={() => handleDeleteCategory(category.id)}>
              Delete
            </button>
          </li>
        ))}
      </ul>
    </div>
  );
}

export default CategoryPage;
