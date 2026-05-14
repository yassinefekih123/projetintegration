"use client";
import React, { useEffect, useState } from "react";
import { useParams, useRouter } from 'next/navigation';
import ConfirmModal from '@/components/ConfirmModal';
import { useToast } from '@/components/ToastProvider';

type OrderItem = { accessoryName: string; quantity: number; priceSnapshot: number };
type Order = { id: string; totalPrice: number; status: string; createdAt: string; items: OrderItem[] };

export default function OrderDetailsPage() {
  const params = useParams();
  const router = useRouter();
  const id = params?.id as string;
  const [order, setOrder] = useState<Order | null>(null);
  const [loading, setLoading] = useState(true);
  const showToast = useToast();
  const [showConfirm, setShowConfirm] = useState(false);

  useEffect(() => {
    if (!id) return;
    const token = typeof window !== 'undefined' ? localStorage.getItem('accessToken') : null;
    fetch(`/api/v1/orders/${id}`, { headers: token ? { Authorization: `Bearer ${token}` } : undefined })
      .then((r) => {
        if (!r.ok) throw new Error('Not found');
        return r.json();
      })
      .then((d) => setOrder(d))
      .catch(() => setOrder(null))
      .finally(() => setLoading(false));
  }, [id]);

  if (loading) return <div className="p-4">Loading...</div>;
  if (!order) return <div className="p-4">Order not found.</div>;

  return (
    <>
    <div className="p-6">
      <div className="flex items-center justify-between mb-4">
        <button className="text-sm text-blue-600" onClick={() => router.back()}>← Back</button>
        {order.status !== 'CANCELLED' && order.status !== 'DELIVERED' && (
          <>
            <button className="px-3 py-1 bg-red-600 text-white rounded text-sm" onClick={() => setShowConfirm(true)}>Cancel Order</button>
            <ConfirmModal show={showConfirm} title="Cancel Order" onCancel={() => setShowConfirm(false)} onConfirm={async () => {
              setShowConfirm(false);
              const token = typeof window !== 'undefined' ? localStorage.getItem('accessToken') : null;
              try {
                const res = await fetch(`/api/v1/orders/${order.id}/cancel`, { method: 'POST', headers: token ? { Authorization: `Bearer ${token}` } : undefined });
                if (res.ok) {
                  const d = await res.json();
                  setOrder(d);
                  showToast('Order cancelled');
                } else {
                  showToast('Failed to cancel order');
                }
              } catch (e) {
                showToast('Failed to cancel order');
              }
            }}>
              <p>Are you sure you want to cancel this order?</p>
            </ConfirmModal>
          </>
        )}
      </div>
      <h1 className="text-2xl font-bold mb-2">Order {order.id}</h1>
      <div className="text-sm text-gray-600 mb-4">Placed: {new Date(order.createdAt).toLocaleString()}</div>
      <div className="mb-4">Status: <span className="font-medium">{order.status}</span></div>
      <div className="mb-4">Total: <span className="font-semibold">${order.totalPrice.toFixed(2)}</span></div>
      <div className="space-y-2">
        {order.items.map((it, idx) => (
          <div key={idx} className="flex justify-between border-b py-2">
            <div>{it.accessoryName} x {it.quantity}</div>
            <div>${it.priceSnapshot.toFixed(2)}</div>
          </div>
        ))}
      </div>
    </div>
    {/* Toasts are rendered by the global ToastProvider */}
    </>
  );
}
