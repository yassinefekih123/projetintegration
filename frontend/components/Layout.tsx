"use client";

import React from 'react';
import Link from 'next/link';
import { useAuth } from './AuthProvider';

export default function Layout({ children }: { children: React.ReactNode }) {
  const { logout } = useAuth();
  return (
    <div className="min-h-screen flex">
      <aside className="w-64 bg-white dark:bg-gray-900 border-r">
        <div className="p-4 font-bold">Accessories Manager</div>
        <nav className="p-4">
          <ul className="space-y-2">
            <li>
              <Link href="/dashboard" className="block px-3 py-2 rounded hover:bg-gray-100">Dashboard</Link>
            </li>
            <li>
              <Link href="/accessories" className="block px-3 py-2 rounded hover:bg-gray-100">Accessories</Link>
            </li>
            <li>
              <Link href="/categories" className="block px-3 py-2 rounded hover:bg-gray-100">Categories</Link>
            </li>
            <li>
              <Link href="/brands" className="block px-3 py-2 rounded hover:bg-gray-100">Brands</Link>
            </li>
            <li>
              <Link href="/orders" className="block px-3 py-2 rounded hover:bg-gray-100">My Orders</Link>
            </li>
            <li>
              <Link href="/orders/create" className="block px-3 py-2 rounded hover:bg-gray-100">Place Order</Link>
            </li>
          </ul>
        </nav>
      </aside>
      <main className="flex-1 bg-gray-50 dark:bg-gray-800 p-6">
        <header className="flex justify-end mb-6">
          <button onClick={logout} className="px-3 py-2 bg-red-500 text-white rounded">Logout</button>
        </header>
        {children}
      </main>
    </div>
  );
}
