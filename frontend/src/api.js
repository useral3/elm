import axios from 'axios';
import qs from 'qs';

const api = axios.create({
  baseURL: 'http://localhost:8080/',
  timeout: 15000,
  headers: { 'Content-Type': 'application/x-www-form-urlencoded' }
});

// 请求拦截器：自动加 Token
api.interceptors.request.use(config => {
  const token = sessionStorage.getItem('token');
  if (token) {
    config.headers.Authorization = `Bearer ${token}`;
  }
  // 路径修正
  if (config.url && config.url.includes('/deliveryaddress/')) {
    config.url = config.url.replace('/deliveryaddress/', '/deliveryAddress/');
  }
  return config;
}, error => Promise.reject(error));

// 响应拦截器：统一错误处理 & Token 过期
api.interceptors.response.use(
  response => response,
  error => {
    if (error.response) {
      const { status, data } = error.response;
      if (status === 401) {
        sessionStorage.removeItem('token');
        sessionStorage.removeItem('user');
        window.location.hash = '#/login';
        return Promise.reject(error);
      }
      const msg = data?.message || `请求失败 (${status})`;
      console.error(`[API Error] ${msg}`);
    } else {
      console.error('[API Error] 网络异常，请检查后端是否启动');
    }
    return Promise.reject(error);
  }
);

/** POST 请求（表单格式） */
export function post(url, params = {}) {
  return api.post(url, qs.stringify(params));
}

/** POST 请求（JSON 格式） */
export function postJSON(url, data = {}) {
  const token = sessionStorage.getItem('token');
  return axios.post('http://localhost:8080' + url, data, {
    timeout: 60000,  // 聊天接口可能较慢，60秒超时
    headers: {
      'Content-Type': 'application/json',
      ...(token ? { Authorization: `Bearer ${token}` } : {})
    }
  });
}

/** GET 请求 */
export function get(url, params = {}) {
  return api.get(url, { params });
}

export default api;
