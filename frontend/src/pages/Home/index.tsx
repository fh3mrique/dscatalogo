import MainImage from '../../assets/imgs/main-image.svg';
import './styles.css';
import ButtonIcon from '../../components/ButtonIcon';
import { Link } from 'react-router-dom';

const Home = () => {
  return (
    <>
      <div className="home-container">
        {/* <h1>{isAuthenticated() ? 'Autenticado' : 'Nao autenticado'}</h1> */}
        <div className="base-card home-card">
          <div className="home-content-container">
            <div>
              <h1>Conheça o melhor catalogo de Produtos</h1>
              <p>
                Ajudaremos você a encontrar os melhores produtos disponíveis no
                mercado.
              </p>
            </div>
            <div>
              <Link to="/products">
                <ButtonIcon text="INICIE AGORA A SUA BUSCA" />
              </Link>
            </div>
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
