import { createContext } from 'react';
import { TokenData } from './util/request';

/* 1° Criando contexto global */

export type AuthContextData = {
  autheticated: boolean;
  tokenData?: TokenData;
};

export type AuthContextType = {
  authContextData: AuthContextData;
  setAuthContextData: (authContextData: AuthContextData) => void;
};

export const AuthContext = createContext<AuthContextType>({
  authContextData: {
    autheticated: false,
  },

  setAuthContextData: () => null,
});
