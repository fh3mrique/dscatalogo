import './styles.css'
import ProductImg from '../../assets/imgs/product.png'

const ProductCard = () => {
  return (
    <div className='base-card product-card'>
        <div className='card-top-container'>
            <img src={ProductImg} alt="" />
        </div>
        <div className='card-bottom-container'>
            <h6>Nome do Produto</h6>
            <p>2334.65</p>
        </div>
    </div>
  )
}

export default ProductCard