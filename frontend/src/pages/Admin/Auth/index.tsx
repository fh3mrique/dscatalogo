import { Navigate, Route, Routes } from 'react-router-dom';
import AuthImage from '../../../assets/imgs/Desenho.svg';

import './styles.css'
import Login from './Login';

const Auth = () => {
  return (
    <div className="auth-container">
      <div className="auth-banner-container">
        <h1>Divulgue seus produtos no DS Catalog</h1>
        <p>
          Faça parte do nosso catálogo de divulgação e aumente a venda dos seus
          produtos.
        </p>
        <img src={AuthImage} alt="" />
      </div> 

      <div className="auth-form-container">
        <Routes>
          <Route path="/" element={<Navigate to="login" replace />} />
          <Route path="/login" element={<Login/>} />
          <Route path="signup" element={<h1>Card signup</h1>} />
          <Route path="recovery" element={<h1>Card recovery</h1>} />
        </Routes>
      </div>
    </div>
  );
};

export default Auth;
