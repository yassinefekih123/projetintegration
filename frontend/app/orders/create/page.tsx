"use client";
import React, { useEffect, useState } from "react";

type Accessory = { id: string; name: string; price: number; stockQuantity?: number };

export default function CreateOrderPage() {
  const [accessories, setAccessories] = useState<Accessory[]>([]);
  const [items, setItems] = useState<{ accessoryId: string; quantity: number }[]>([]);
  const [loading, setLoading] = useState(true);
  const [message, setMessage] = useState<string | null>(null);

  useEffect(() => {
    fetch("/api/v1/accessories?page=0&size=50")
      .then((r) => r.json())
      .then((data) => {
        // handle either wrapped or raw responses
        const list = data.content || data;
        setAccessories(list.map((a: any) => ({ id: a.id, name: a.name, price: a.price })));
      })
      .catch(() => setAccessories([]))
      .finally(() => setLoading(false));
  }, []);

  function addItem() {
    setItems([...items, { accessoryId: accessories[0]?.id || "", quantity: 1 }]);
  }

  function updateItem(idx: number, val: Partial<{ accessoryId: string; quantity: number }>) {
    const copy = [...items];
    copy[idx] = { ...copy[idx], ...val };
    setItems(copy);
  }

  function removeItem(idx: number) {
    setItems(items.filter((_, i) => i !== idx));
  }

  function submit() {
    const token = typeof window !== "undefined" ? localStorage.getItem("accessToken") : null;
    fetch("/api/v1/orders", {
      method: "POST",
      headers: {
        "Content-Type": "application/json",
        ...(token ? { Authorization: `Bearer ${token}` } : {}),
      },
      body: JSON.stringify({ items }),
    })
      .then((r) => {
        if (!r.ok) throw new Error("Order failed");
        return r.json();
      })
      .then((d) => setMessage("Order placed successfully"))
      .catch((e) => setMessage(String(e)));
  }

  if (loading) return <div className="p-4">Loading accessories...</div>;

  return (
    <div className="p-6">
      <h1 className="text-2xl font-bold mb-4">Place Order</h1>
      {message && <div className="mb-4 text-green-600">{message}</div>}
      <div className="space-y-3">
        {items.map((it, idx) => (
          <div key={idx} className="flex gap-2 items-center">
            <select value={it.accessoryId} onChange={(e) => updateItem(idx, { accessoryId: e.target.value })} className="border p-2 rounded">
              {accessories.map((a) => <option key={a.id} value={a.id}>{a.name} — ${a.price}</option>)}
            </select>
            <input type="number" value={it.quantity} min={1} onChange={(e) => updateItem(idx, { quantity: Number(e.target.value) })} className="w-20 border p-2 rounded" />
            <button onClick={() => removeItem(idx)} className="text-sm text-red-600">Remove</button>
          </div>
        ))}
      </div>
      <div className="mt-4">
        <button onClick={addItem} className="px-4 py-2 bg-blue-600 text-white rounded mr-2">Add Item</button>
        <button onClick={submit} className="px-4 py-2 bg-green-600 text-white rounded">Place Order</button>
      </div>
    </div>
  );
}
