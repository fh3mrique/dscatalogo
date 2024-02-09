import './styles.css';
const NavBar = () => {
  return (
    <nav className="admin-nav-container">
      <ul>
        <li>
          <a href="" className='admin-nav-item active'>
            <p>Produtos</p>
          </a>
        </li>
        <li>
          <a href="" className='admin-nav-item'>
            <p>Categorias</p>
          </a>
        </li>
        <li>
          <a href="" className='admin-nav-item'>
            <p>Usúarios</p>
          </a>
        </li>
      </ul>
    </nav>
  );
};

export default NavBar;
