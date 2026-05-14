"use client";

import React, { useState } from 'react';
import api from '@/lib/api';
import Layout from '@/components/Layout';
import { useRouter } from 'next/navigation';

export default function CreateAccessory() {
  const [name, setName] = useState('');
  const [price, setPrice] = useState('0');
  const [stock, setStock] = useState('0');
  const [file, setFile] = useState<File | null>(null);
  const router = useRouter();

  const submit = async (e: React.FormEvent) => {
    e.preventDefault();
    let imageUrl = undefined;
    if (file) {
      const fd = new FormData();
      fd.append('file', file);
      const res = await api.post('/uploads', fd, { headers: { 'Content-Type': 'multipart/form-data' } });
      const payload = res.data.data || res.data;
      imageUrl = payload.url;
    }
    await api.post('/accessories', { name, price: Number(price), stockQuantity: Number(stock), imageUrl });
    router.push('/accessories');
  };

  return (
    <Layout>
      <div className="max-w-2xl bg-white p-6 rounded shadow">
        <h2 className="text-xl font-semibold mb-4">Create Accessory</h2>
        <form onSubmit={submit} className="space-y-3">
          <input value={name} onChange={(e) => setName(e.target.value)} placeholder="Name" className="w-full p-2 border rounded" />
          <input value={price} onChange={(e) => setPrice(e.target.value)} placeholder="Price" className="w-full p-2 border rounded" />
          <input value={stock} onChange={(e) => setStock(e.target.value)} placeholder="Stock" className="w-full p-2 border rounded" />
          <input type="file" onChange={(e) => setFile(e.target.files?.[0] || null)} />
          <button className="px-4 py-2 bg-blue-600 text-white rounded">Create</button>
        </form>
      </div>
    </Layout>
  );
}
