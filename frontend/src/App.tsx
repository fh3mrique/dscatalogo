import './assets/styles/custom.scss';
import './App.css';
import Rotas from './Rotas';
import { useState } from 'react';
import { AuthContext, AuthContextData } from './AuthContext';

/* 2° Prover o contexto global
Crie um useState no App.tsx */

/* 04. CRUD, paginação, filtros */

function App() {
  const [authContextData, setAuthContextData] = useState<AuthContextData>({
    autheticated: false,
  });
  return (
    <>
      <AuthContext.Provider value={{ authContextData, setAuthContextData }}>
        <Rotas />
      </AuthContext.Provider>
    </>
  );
}

export default App;
