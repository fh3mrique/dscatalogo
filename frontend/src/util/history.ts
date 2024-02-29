
/* ignore quando for implementar rotas privadas */
import { useNavigate } from 'react-router-dom';

// Função que retorna a instância de useNavigate
export const useHistoryInstance = () => useNavigate();