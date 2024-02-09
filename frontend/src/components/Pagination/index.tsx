import ArrowIcon from '../../assets/imgs/Seta.svg'
import './styles.css'

const Pagination = () => {
  return (
    <div className='pagination-container'>
        <img src={ArrowIcon} alt="" className='arrow-previous arrow-inative'/>
        <div className='pagination-item active'>1</div>
        <div className='pagination-item'>2</div>
        <div className='pagination-item'>3</div>
        <div className='pagination-item'>...</div>
        <div className='pagination-item'>10</div>
        <img src={ArrowIcon} alt="" className='arrow-next arrow-active' />
    </div>
  )
}

export default Pagination