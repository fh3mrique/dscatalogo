import ArrowIcon from '../../assets/imgs/Seta.svg'

const Pagination = () => {
  return (
    <div className='pagination-container'>
        <img src={ArrowIcon} alt="" />
        <div className='pagination-item'>1</div>
        <div className='pagination-item'>2</div>
        <div className='pagination-item'>3</div>
        <div className='pagination-item'>...</div>
        <div className='pagination-item'>10</div>
        <img src={ArrowIcon} alt="" />
    </div>
  )
}

export default Pagination