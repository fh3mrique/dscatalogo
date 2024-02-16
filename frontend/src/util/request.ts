export const BASE_URL = 'http://localhost:8080';
import axios from 'axios';
import qs from 'qs';

const CLIENT_ID = 'dscatalog';
const CLIENT_SECRET = 'dscatalog123';

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
    'Authorization': basicHeader()
  };

  const data = qs.stringify({
    ...loginData,
    grant_type: 'password'
  });

  return axios({
    method: 'POST',
    baseURL: BASE_URL,
    url: '/oauth/token',
    data: data,
    headers: headers,
  });
};
