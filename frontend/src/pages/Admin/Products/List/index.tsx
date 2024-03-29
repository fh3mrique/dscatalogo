import { Link } from 'react-router-dom';
import ProductCrudCard from '../ProductCrudCard';
import './styles.css';
import { Product } from '../../../../types/product';
import { SpringPage } from '../../../../types/vendor/spring';
import { useEffect, useState } from 'react';
import { requestBackend } from '../../../../util/request';
import { AxiosRequestConfig } from 'axios';

const List = () => {
  const [page, setPage] = useState<SpringPage<Product>>();

  useEffect(() => {
    const params: AxiosRequestConfig = {
      method: 'GET',
      url: `/products`,
      params: {
        page: 0,
        size: 90,
      },
    };

    requestBackend(params).then((response) => {
      setPage(response.data);
    });
  }, []);

  return (
    <div className="product-crud-container">
      <div className="product-crud-bar-container">
        <Link to="/admin/products/create">
          <button className="btn btn-primary text-white btn-crud-add">
            ADICIONAR
          </button>
        </Link>
        <div className="base-card product-filter-container">Search bar</div>
      </div>

      <div className="row">
        {page?.content.map((product) => (
          <div key={product.id} className="col-sm-6 col-md-12">
            <ProductCrudCard product={product} />
          </div>
        ))}
      </div>
    </div>
  );
};

export default List;
