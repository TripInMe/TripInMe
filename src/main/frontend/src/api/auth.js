import { api } from './client';

export const signup = (data) =>
  api('/api/auth/signup', { method:'POST', body: JSON.stringify(data) });

export const login = (data) =>
  api('/api/auth/login',  { method:'POST', body: JSON.stringify(data) });

export const logout = () =>
  api('/api/auth/logout', { method:'POST' });

export const me = () => api('/api/auth/me');
