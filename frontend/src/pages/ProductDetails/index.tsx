import ProductPrice from '../../components/ProductPrice';
import ArrowIcon from '../../assets/imgs/Seta.svg';
import './styles.css';
import { Link } from 'react-router-dom';
import axios from 'axios';
import { BASE_URL } from '../../util/request';
import { useEffect, useState } from 'react';
import { Product } from '../../types/product';

const ProductDetails = () => {

  const [product, setProduct] = useState<Product>();

 useEffect(() =>{
  axios.get(BASE_URL + "/products/2")
  .then(response => {
    setProduct(response.data)
  });
 }, [])
  return (
    <div className="product-details-container">
      <div className=" base-card product-details-card">
        <Link to="/products">
          <div className="goback-container">
            <img src={ArrowIcon} alt="" />
            <h2>VOLTAR</h2>
          </div>
        </Link>

        <div className="row">
          <div className="col-xl-6">
            <div className="img-container">
              <img
                src={product?.imgUrl}
                alt={product?.name}
              />
            </div>
            <div className="name-price-container">
              <h1>{product?.name}</h1>
              {product && <ProductPrice price={product?.price} />}
            </div> 
          </div>
          <div className="col-xl-6">
            <div className="description-container">
              <h2>Descrição do produto</h2>
              <p>
                {product?.description}
              </p>
            </div>
          </div>
        </div>
      </div>
    </div>
  );
};

export default ProductDetails;
