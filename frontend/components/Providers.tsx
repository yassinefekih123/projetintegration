"use client";
import React from 'react';
import { AuthProvider } from './AuthProvider';
import { ToastProvider } from './ToastProvider';

export default function Providers({ children }: { children: React.ReactNode }) {
  return (
    <AuthProvider>
      <ToastProvider>{children}</ToastProvider>
    </AuthProvider>
  );
}
