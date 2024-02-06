import ProductCard from '../../components/ProductCard';
import NavBar from '../../components/NavBar';

const Catalog = () => {
  return (
    <>
      <NavBar />
      <div className='container my-4'>
      <ProductCard/>
      </div>
    </>
  );
};

export default Catalog;
