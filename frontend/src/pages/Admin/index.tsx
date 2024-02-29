import { Navigate, Route, Routes } from 'react-router-dom';
import NavBar from './NavBar';
import './styles.css';
import Users from './Users';
import Products from './Products';

// @ts-ignore
const PrivateRoute = ({ children, redirectTo }) => {
  const isAuthenticated = localStorage.getItem('authData') !== null;
  console.log('isAuth: ', isAuthenticated);
  return isAuthenticated ? children : <Navigate to={redirectTo} />;
};

const Admin = () => {
  return (
    <div className="admin-container">
      <NavBar />
      <div className="admin-content">
        <Routes>
          <Route path="/" element={<Navigate to="products" replace />} />
          {/* <Route path="products" element={<h1>Product CRUD</h1>} />
          <Route path="categories" element={<h1>Category CRUD</h1>} />
          <Route path="users" element={<Users />} /> */}

          <Route
            path="products/*"
            element={
              <PrivateRoute redirectTo="/admin/auth/login">
                <Products/>
              </PrivateRoute>
            }
          />
          <Route
            path="categories"
            element={
              <PrivateRoute redirectTo="/admin/auth/login">
                {<h1>Categories CRUD</h1>}
              </PrivateRoute>
            }
          />
          <Route
            path="users"
            element={
              <PrivateRoute redirectTo="/admin/auth/login">
                <Users />
              </PrivateRoute>
            }
          />
        </Routes>
      </div>
    </div>
  );
};

export default Admin;
