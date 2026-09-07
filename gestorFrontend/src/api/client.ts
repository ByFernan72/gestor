import axios, { AxiosInstance } from 'axios';

const api: AxiosInstance = axios.create({
  baseURL: '/api/v1',
  headers: {
    'Content-Type': 'application/json',
  },
  timeout: 10000,
});

api.interceptors.response.use(
  (response) => response,
  (error) => {
    // Interceptor global para loggear o manejar errores comunes
    if (error.response?.status === 401) {
      console.warn('Sesión no autorizada o expirada.');
    }
    return Promise.reject(error);
  }
);

export default api;
