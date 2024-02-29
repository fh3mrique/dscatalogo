export const BASE_URL = 'http://localhost:8080';
import axios, { AxiosRequestConfig } from 'axios';
import qs from 'qs';

/* import { useHistoryInstance } from './history'; */

const CLIENT_ID = 'dscatalog';
const CLIENT_SECRET = 'dscatalog123';

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

export const isAuthenticated = (): boolean => {
  const authData = getAuthData();
  // Verifica se há dados de autenticação e se o token de acesso está presente e não está vazio
  return !!authData && !!authData.access_token;
};

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
