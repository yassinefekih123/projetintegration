"use client";

import React, { createContext, useContext, useEffect, useState } from 'react';
import api from '@/lib/api';
import { getToken, saveToken, clearToken } from '@/lib/auth';
import { useRouter } from 'next/navigation';

type AuthContextType = {
  token: string | null;
  login: (email: string, password: string) => Promise<void>;
  register: (firstName: string, lastName: string, email: string, password: string) => Promise<void>;
  logout: () => void;
};

const AuthContext = createContext<AuthContextType | undefined>(undefined);

export function useAuth() {
  const ctx = useContext(AuthContext);
  if (!ctx) throw new Error('useAuth must be used within AuthProvider');
  return ctx;
}

export const AuthProvider = ({ children }: { children: React.ReactNode }) => {
  const [token, setToken] = useState<string | null>(null);
  const router = useRouter();

  useEffect(() => {
    const t = getToken();
    if (t) setToken(t);
  }, []);

  useEffect(() => {
    if (token) {
      api.defaults.headers.common['Authorization'] = `Bearer ${token}`;
      saveToken(token);
    } else {
      delete api.defaults.headers.common['Authorization'];
      clearToken();
    }
  }, [token]);

  const login = async (email: string, password: string) => {
    const res = await api.post('/auth/login', { email, password });
    setToken(res.data.accessToken || res.data.data?.accessToken || null);
    router.push('/dashboard');
  };

  const register = async (firstName: string, lastName: string, email: string, password: string) => {
    const res = await api.post('/auth/register', { firstName, lastName, email, password });
    setToken(res.data.accessToken || res.data.data?.accessToken || null);
    router.push('/dashboard');
  };

  const logout = () => {
    setToken(null);
    router.push('/login');
  };

  return <AuthContext.Provider value={{ token, login, register, logout }}>{children}</AuthContext.Provider>;
};
