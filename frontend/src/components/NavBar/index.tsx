import { Link } from 'react-router-dom';
import './styles.css';
import 'bootstrap/js/src/collapse.js'
import { NavLink } from 'react-router-dom';

const NavBar = () => {
  return (
    <nav className="main-nav bg-primary navbar navbar-expand-md">
      <div className="container-fluid">
        <Link to="/" className="nav-logo-text">
          <h4>DS Catalog</h4>
        </Link>

        <button
          className="navbar-toggler"
          type="button"
          data-bs-toggle="collapse"
          data-bs-target="#dscatalog-navbar"
          aria-controls="dscatalog-navbar"
          aria-expanded="false"
          aria-label="Toggle navigation"
        >
          <span className="navbar-toggler-icon"></span>
        </button>

        <div className="collapse navbar-collapse" id='dscatalog-navbar'>
          <ul className="navbar-nav offset-md-2 main-menu">
            <li>
              <NavLink className="active" to="/" >
                HOME
              </NavLink>
            </li>
            <li>
              <NavLink to="products">CATÁLOGO</NavLink>
            </li>
            <li>
              <NavLink to="admin">ADMIN</NavLink>
            </li>
          </ul>
        </div>
      </div>
    </nav>
  );
};

export default NavBar;
