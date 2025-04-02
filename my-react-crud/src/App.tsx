import React, { useEffect, useState } from 'react';
import './App.css';

interface Product {
  id: number;
  name: string;
  description: string;
  price: number;
}

const App: React.FC = () => {
  const [products, setProducts] = useState<Product[]>([]);
  const [newProduct, setNewProduct] = useState<Omit<Product, 'id'>>({
    name: '',
    description: '',
    price: 0,
  });
  const [editingProduct, setEditingProduct] = useState<Product | null>(null);

  const backendUrl = 'http://localhost:8080/products';

  const fetchProducts = async () => {
    try {
      const res = await fetch(backendUrl);
      const data = await res.json();
      setProducts(data);
    } catch (error) {
      console.error('Error fetching products:', error);
    }
  };

  useEffect(() => {
    fetchProducts();
  }, []);

  const handleInputChange = (
    e: React.ChangeEvent<HTMLInputElement | HTMLTextAreaElement>
  ) => {
    setNewProduct({ ...newProduct, [e.target.name]: e.target.value });
  };

  const handleSubmit = async (e: React.FormEvent) => {
    e.preventDefault();

    if (editingProduct) {
      // Оновлення продукту
      const res = await fetch(`${backendUrl}/${editingProduct.id}`, {
        method: 'PUT',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify(newProduct),
      });
      if (res.ok) {
        fetchProducts();
        setEditingProduct(null);
        setNewProduct({ name: '', description: '', price: 0 });
      }
    } else {
      // Створення нового продукту
      const res = await fetch(backendUrl, {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify(newProduct),
      });
      if (res.ok) {
        fetchProducts();
        setNewProduct({ name: '', description: '', price: 0 });
      }
    }
  };

  const handleEdit = (product: Product) => {
    setEditingProduct(product);
    setNewProduct({
      name: product.name,
      description: product.description,
      price: product.price,
    });
  };

  const handleDelete = async (id: number) => {
    await fetch(`${backendUrl}/${id}`, { method: 'DELETE' });
    fetchProducts();
  };

  return (
    <div className="App">
      <h1>Product CRUD</h1>
      <form onSubmit={handleSubmit}>
        <input
          type="text"
          name="name"
          placeholder="Name"
          value={newProduct.name}
          onChange={handleInputChange}
          required
        />
        <textarea
          name="description"
          placeholder="Description"
          value={newProduct.description}
          onChange={handleInputChange}
          required
        ></textarea>
        <input
          type="number"
          name="price"
          placeholder="Price"
          value={newProduct.price}
          onChange={handleInputChange}
          required
        />
        <button type="submit">
          {editingProduct ? 'Update Product' : 'Add Product'}
        </button>
      </form>
      <hr />
      <ul>
        {products.map((product) => (
          <li key={product.id}>
            <strong>{product.name}</strong> - {product.description} - ${product.price.toFixed(2)}
            <button onClick={() => handleEdit(product)}>Edit</button>
            <button onClick={() => handleDelete(product.id)}>Delete</button>
          </li>
        ))}
      </ul>
    </div>
  );
};

export default App;
