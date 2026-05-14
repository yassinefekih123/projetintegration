"use client";

import React, { useState } from 'react';
import { AuthProvider, useAuth } from '@/components/AuthProvider';

function RegisterForm() {
  const { register } = useAuth();
  const [firstName, setFirstName] = useState('');
  const [lastName, setLastName] = useState('');
  const [email, setEmail] = useState('');
  const [password, setPassword] = useState('');
  const [loading, setLoading] = useState(false);

  const submit = async (e: React.FormEvent) => {
    e.preventDefault();
    setLoading(true);
    try {
      await register(firstName, lastName, email, password);
    } catch (err) {
      alert('Register failed');
    } finally {
      setLoading(false);
    }
  };

  return (
    <div className="max-w-md mx-auto mt-12 p-8 bg-white rounded shadow">
      <h1 className="text-2xl font-semibold mb-4">Create account</h1>
      <form onSubmit={submit} className="space-y-3">
        <input value={firstName} onChange={(e) => setFirstName(e.target.value)} placeholder="First name" className="w-full p-2 border rounded" />
        <input value={lastName} onChange={(e) => setLastName(e.target.value)} placeholder="Last name" className="w-full p-2 border rounded" />
        <input value={email} onChange={(e) => setEmail(e.target.value)} placeholder="Email" className="w-full p-2 border rounded" />
        <input type="password" value={password} onChange={(e) => setPassword(e.target.value)} placeholder="Password" className="w-full p-2 border rounded" />
        <button type="submit" disabled={loading} className="w-full bg-green-600 text-white p-2 rounded">{loading ? 'Creating...' : 'Create account'}</button>
      </form>
    </div>
  );
}

export default function RegisterPage() {
  return (
    <AuthProvider>
      <RegisterForm />
    </AuthProvider>
  );
}
