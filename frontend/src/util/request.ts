export const BASE_URL = 'http://localhost:8080';
import axios, { AxiosRequestConfig } from 'axios';
import qs from 'qs';
import { jwtDecode } from 'jwt-decode';

/* import { useHistoryInstance } from './history'; */

const CLIENT_ID = 'dscatalog';
const CLIENT_SECRET = 'dscatalog123';

type Role = 'ROLE_OPERATOR' | 'ROLE_ADMIN';

export type TokenData = {
  exp: number;
  user_name: string;
  authorities: Role[];
};

type loginResponse = {
  access_token: string;
  token_type: string;
  expires_in: number;
  scope: string;
  userFirstName: string;
  userId: number;
};

const basicHeader = () => {
  return 'Basic ' + window.btoa(CLIENT_ID + ':' + CLIENT_SECRET);
};

type loginData = {
  username: string;
  password: string;
};

export const requestBackendLogin = (loginData: loginData) => {
  const headers = {
    'Content-Type': 'application/x-www-form-urlencoded',
    Authorization: basicHeader(),
  };

  const data = qs.stringify({
    ...loginData,
    grant_type: 'password',
  });

  return axios({
    method: 'POST',
    baseURL: BASE_URL,
    url: '/oauth/token',
    data: data,
    headers: headers,
  });
};

export const requestBackend = (config: AxiosRequestConfig) => {
  const headers = config.withCredentials
    ? {
        ...config.headers,
        Authorization: 'Bearer ' + getAuthData().access_token,
      }
    : config.headers;

  return axios({ ...config, baseURL: BASE_URL, headers: headers });
};

export const saveAuthData = (obj: loginResponse) => {
  localStorage.setItem('authData', JSON.stringify(obj));
};

export const getAuthData = () => {
  const str = localStorage.getItem('authData') ?? '{}';
  const obj = JSON.parse(str);
  return obj as loginResponse;
};

export const removeAuthData = () => {
  localStorage.removeItem('authData');
};

/* export const isAuthenticated = (): boolean => {
  const authData = getAuthData();
  // Verifica se há dados de autenticação e se o token de acesso está presente e não está vazio
  return !!authData && !!authData.access_token;
}; */

/* ignore quando for implementar rotas privadas */

/* // Obtém a instância de useNavigate
const history = useHistoryInstance();

// Add a request interceptor
axios.interceptors.request.use(function (config) {
  console.log("INTERCEPTOR ANTES DA REQUISIÇÃO");
  return config;
}, function (error) {
  console.log("INTERCEPTOR ANTES DA REQUISIÇÃO");
  return Promise.reject(error);
});

// Add a response interceptor
axios.interceptors.response.use(function (response) {
  console.log("INTERCEPTOR RESPOSTA COM SUCESSO");
  return response;
}, function (error) {
  if (error.response.status === 401 || error.response.status === 403){
    history("/admin/auth"); // Use a instância de useNavigate() para redirecionamento
  }
  console.log("INTERCEPTOR RESPOSTA COM ERROR");
  return Promise.reject(error);
}); */

export const getTokenData = (): TokenData | undefined => {
  const loginResponse = getAuthData();

  try {
    return jwtDecode(loginResponse.access_token) as TokenData;
  } catch (error) {
    return undefined;
  }
};

export const isAuthenticated = (): boolean => {
  //Decodificar o token (pode lançar exceção)
  const tokenData = getTokenData();

  return tokenData && tokenData.exp * 1000 > Date.now() ? true : false;
};

export const hasAnyRole = (roles: Role[]): boolean =>{

  if (roles.length === 0){
    return true;
  }

  const tokenData = getTokenData();

  if (tokenData !== undefined){
    for (var i = 0; i < roles.length; i++){
      if (tokenData.authorities.includes(roles[i])){
        return true;
      }
    }
  }

  return false;
}
