import { Navigate, Route, Routes } from 'react-router-dom';
import NavBar from './NavBar';
import './styles.css';
import Users from './User';

const Admin = () => {
  return (
    <div className="admin-container">
      <NavBar />
      <div className="admin-content">
        <Routes>
        <Route path="/" element={<Navigate to="products" replace />} />
          <Route path="products" element={<h1>Product CRUD</h1>} />
          <Route path="categories" element={<h1>Category CRUD</h1>} />
          <Route path="users" element={<Users/>} />
        </Routes>
      </div>
    </div>
  );
};

export default Admin;
