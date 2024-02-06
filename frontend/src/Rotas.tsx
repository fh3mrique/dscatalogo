import Catalog from "./pages/Catalog"
import Admin from "./pages/Admin"
import Home from "./pages/Home"
import NavBar from "./components/NavBar"
import { BrowserRouter, Route, Routes } from "react-router-dom"


const Rotas = () => {
  return (
    <BrowserRouter>
        <NavBar/>
        <Routes>
            <Route path="/" element={<Home/>}/>
            <Route path="/products" element={<Catalog/>}/>      
            <Route path="/admin" element={<Admin/>}/>      
        </Routes>
    </BrowserRouter>
  )
}

export default Rotas