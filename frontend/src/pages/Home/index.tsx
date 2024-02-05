import NavBar from "../../components/NavBar"
import MainImage from "../../assets/imgs/main-image.svg"
import './styles.css'
import ButtonIcon from "../../components/ButtonIcon"


const Home = () => {
  return (
    <>
      <NavBar />
      <div className="home-container">
         <div className="home-card">
            <div className="home-content-container">
                <h1>Conheça o melhor catalogo de Produtos</h1>
                <p>Ajudaremos você a encontrar os melhores produtos disponíveis no mercado.</p>
                <ButtonIcon/>
            </div>
            <div className="home-image-container">
                <img src={MainImage} alt="" />
            </div>
         </div>
      </div>
    </>
  )
}

export default Home