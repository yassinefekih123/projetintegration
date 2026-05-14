"use client";
import React, { useEffect, useState } from "react";
import Link from 'next/link';

type OrderItem = {
  accessoryName: string;
  quantity: number;
  priceSnapshot: number;
};

type Order = {
  id: string;
  totalPrice: number;
  status: string;
  createdAt: string;
  items: OrderItem[];
};

export default function OrdersPage() {
  const [orders, setOrders] = useState<Order[] | null>(null);
  const [loading, setLoading] = useState(true);

  useEffect(() => {
    const token = typeof window !== "undefined" ? localStorage.getItem("accessToken") : null;
    fetch("/api/v1/orders", {
      headers: token ? { Authorization: `Bearer ${token}` } : undefined,
    })
      .then((r) => r.json())
      .then((data) => setOrders(data))
      .catch(() => setOrders([]))
      .finally(() => setLoading(false));
  }, []);

  if (loading) return <div className="p-4">Loading...</div>;
  if (!orders || orders.length === 0) return <div className="p-4">No orders yet.</div>;

  return (
    <div className="p-6">
      <h1 className="text-2xl font-bold mb-4">My Orders</h1>
      <div className="space-y-4">
        {orders.map((o) => (
          <div key={o.id} className="border rounded p-4">
            <div className="flex justify-between items-center">
              <div>
                <div className="font-semibold">
                  <Link href={`/orders/${o.id}`} className="text-blue-600 hover:underline">Order {o.id}</Link>
                </div>
                <div className="text-sm text-gray-500">{new Date(o.createdAt).toLocaleString()}</div>
              </div>
              <div className="text-right">
                <div className="font-medium">${o.totalPrice.toFixed(2)}</div>
                <div className="text-sm">{o.status}</div>
              </div>
            </div>
            <div className="mt-3">
              {o.items.map((it, idx) => (
                <div key={idx} className="flex justify-between text-sm">
                  <div>{it.accessoryName} x {it.quantity}</div>
                  <div>${it.priceSnapshot.toFixed(2)}</div>
                </div>
              ))}
            </div>
          </div>
        ))}
      </div>
    </div>
  );
}
