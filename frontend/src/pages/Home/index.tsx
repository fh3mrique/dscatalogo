import MainImage from '../../assets/imgs/main-image.svg';
import './styles.css';
import ButtonIcon from '../../components/ButtonIcon';
import { Link } from 'react-router-dom';

const Home = () => {
  return (
    <>
      <div className="home-container">
        <div className="base-card home-card">
          <div className="home-content-container">
            <div>
              <h1>Conheça o melhor catalogo de Produtos</h1>
              <p>
                Ajudaremos você a encontrar os melhores produtos disponíveis no
                mercado.
              </p>
            </div>
            <Link to="/products">
              <ButtonIcon />
            </Link>
          </div>
          <div className="home-image-container">
            <img src={MainImage} alt="" />
          </div>
        </div>
      </div>
    </>
  );
};

export default Home;
