import React, { useEffect, useState } from 'react';
import './App.css';

interface User {
  id: number;
  name: string;
  email: string;
}

const App: React.FC = () => {
  const [users, setUsers] = useState<User[]>([]);
  const [name, setName] = useState('');
  const [email, setEmail] = useState('');
  const [editingUser, setEditingUser] = useState<User | null>(null);


  const fetchUsers = async () => {
    try {
      const response = await fetch('http://localhost:8080/users');
      const data = await response.json();
      setUsers(data);
    } catch (error) {
      console.error('Error fetching users:', error);
    }
  };

  useEffect(() => {
    fetchUsers();
  }, []);

  const handleSubmit = async (e: React.FormEvent) => {
    e.preventDefault();
    if (editingUser) {

      await fetch(`http://localhost:8080/users/${editingUser.id}`, {
        method: 'PUT',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify({ name, email })
      });
      setEditingUser(null);
    } else {

      await fetch('http://localhost:8080/users', {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify({ name, email })
      });
    }
    setName('');
    setEmail('');
    fetchUsers();
  };

  const handleEdit = (user: User) => {
    setEditingUser(user);
    setName(user.name);
    setEmail(user.email);
  };

  const handleDelete = async (id: number) => {
    await fetch(`http://localhost:8080/users/${id}`, {
      method: 'DELETE'
    });
    fetchUsers();
  };

  return (
    <div className="App">
      <h1>CRUD з React (TypeScript) + Spring Boot</h1>


      <form onSubmit={handleSubmit}>
        <input
          type="text"
          placeholder="Ім'я"
          value={name}
          onChange={(e) => setName(e.target.value)}
          required
        />
        <input
          type="email"
          placeholder="Email"
          value={email}
          onChange={(e) => setEmail(e.target.value)}
          required
        />
        <button type="submit">
          {editingUser ? 'Оновити' : 'Додати'}
        </button>
      </form>


      <ul>
        {users.map(user => (
          <li key={user.id}>
            {user.name} ({user.email})
            <button onClick={() => handleEdit(user)}>Редагувати</button>
            <button onClick={() => handleDelete(user.id)}>Видалити</button>
          </li>
        ))}
      </ul>
    </div>
  );
};

export default App;

