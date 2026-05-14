"use client";

import React, { useEffect, useState } from 'react';
import api from '@/lib/api';
import Layout from '@/components/Layout';
import AccessoriesTable from '@/components/AccessoriesTable';
import Pagination from '@/components/Pagination';
import ConfirmModal from '@/components/ConfirmModal';
import { AccessoryDto } from '@/types';

export default function AccessoriesPage() {
  const [items, setItems] = useState<AccessoryDto[]>([]);
  const [page, setPage] = useState(0);
  const [size] = useState(10);
  const [total, setTotal] = useState(0);
  const [loading, setLoading] = useState(false);
  const [q, setQ] = useState('');
  const [confirmOpen, setConfirmOpen] = useState(false);
  const [selectedId, setSelectedId] = useState<string | null>(null);

  const load = async () => {
    setLoading(true);
    try {
      const res = await api.get('/accessories', { params: { q, page, size } });
      const data = res.data;
      // handle ApiResponse wrapper if present
      const payload = data.data || data;
      setItems(payload.content || payload);
      setTotal(payload.totalElements || payload.length || 0);
    } catch (err) {
      console.error(err);
    } finally {
      setLoading(false);
    }
  };

  useEffect(() => { load(); }, [page, q]);

  const onDelete = (id: string) => {
    setSelectedId(id);
    setConfirmOpen(true);
  };

  const confirmDelete = async () => {
    if (!selectedId) return;
    await api.delete(`/accessories/${selectedId}`);
    setConfirmOpen(false);
    setSelectedId(null);
    load();
  };

  return (
    <Layout>
      <div>
        <div className="flex items-center justify-between mb-4">
          <h1 className="text-2xl font-semibold">Accessories</h1>
          <div className="flex gap-2">
            <input value={q} onChange={(e) => setQ(e.target.value)} placeholder="Search" className="p-2 border rounded" />
            <button onClick={() => load()} className="px-3 py-2 bg-blue-600 text-white rounded">Search</button>
          </div>
        </div>

        {loading ? <div className="p-4">Loading...</div> : <AccessoriesTable items={items} onDelete={onDelete} />}

        <Pagination page={page} size={size} total={total} onPage={(p) => setPage(p)} />

        <ConfirmModal show={confirmOpen} title="Delete accessory" onConfirm={confirmDelete} onCancel={() => setConfirmOpen(false)}>Delete this accessory?</ConfirmModal>
      </div>
    </Layout>
  );
}

