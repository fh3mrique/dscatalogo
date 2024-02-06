import Catalog from "./pages/Catalog"
import Admin from "./pages/Admin"
import Home from "./pages/Home"
import NavBar from "./components/NavBar"
import { BrowserRouter, Route, Routes } from "react-router-dom"
import ProductDetails from "./pages/ProductDetails"


const Rotas = () => {
  return (
    <BrowserRouter>
        <NavBar/>
        <Routes>
            <Route path="/" element={<Home/>}/>
            <Route path="/products" element={<Catalog/>}/>      
            <Route path="/products/:productId" element={<ProductDetails/>}/>      
            <Route path="/admin" element={<Admin/>}/>      
        </Routes>
    </BrowserRouter>
  )
}

export default Rotas