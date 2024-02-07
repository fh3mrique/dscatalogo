import ProductPrice from '../../components/ProductPrice'
import ArrowIcon from '../../assets/imgs/Seta.svg'

const ProductDetails = () => {
  return (
    <div className="product-details-container">
        <div className="product-details-card">

            <div className="goback-container">
                <img src={ArrowIcon} alt="" />
                <h2>VOLTAR</h2>
            </div>

            <div className="row">
                <div className="col-xl-6">
                    <div className="img-container">
                        <img src="https://raw.githubusercontent.com/devsuperior/dscatalog-resources/master/backend/img/1-big.jpg" alt="" />
                    </div>
                    <div className="name-price-container">
                        <h1>Nome do produto</h1>
                        <ProductPrice price={2304.04}/>
                    </div>
                </div>
                <div className="col-xl-6">
                     <div className="description-container">
                        <h2>Descrição do produto</h2>
                        <p>Lorem ipsum dolor, sit amet consectetur adipisicing elit. Labore, sit?</p>
                     </div>
                </div>
            </div>


        </div>
    </div>
  )
}

export default ProductDetails