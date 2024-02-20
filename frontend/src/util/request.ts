export const BASE_URL = 'http://localhost:8080';
import axios, { AxiosRequestConfig } from 'axios';
import qs from 'qs';

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
