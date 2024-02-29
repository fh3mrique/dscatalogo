import { Route, Routes } from 'react-router-dom';
import Form from './Form';
import List from './List';

const Products = () => {
  return (
    <div>
      <Routes>
        <Route path="/" element={<List/>} />
        <Route path=":productId" element={<Form/>} />
      </Routes>
    </div>
  );
};

export default Products;
