"use client";

import React from 'react';
import { AccessoryDto } from '../types';

export default function AccessoriesTable({ items, onDelete }: { items: AccessoryDto[]; onDelete: (id: string) => void; }) {
  return (
    <div className="bg-white dark:bg-gray-900 rounded shadow overflow-x-auto">
      <table className="min-w-full text-left">
        <thead className="bg-gray-50 dark:bg-gray-800">
          <tr>
            <th className="p-3">Name</th>
            <th className="p-3">Price</th>
            <th className="p-3">Stock</th>
            <th className="p-3">Brand</th>
            <th className="p-3">Category</th>
            <th className="p-3">Actions</th>
          </tr>
        </thead>
        <tbody>
          {items.map((it) => (
            <tr key={it.id} className="border-t">
              <td className="p-3">{it.name}</td>
              <td className="p-3">${it.price?.toString()}</td>
              <td className="p-3">{it.stockQuantity}</td>
              <td className="p-3">{it.brandName}</td>
              <td className="p-3">{it.categoryName}</td>
              <td className="p-3">
                <button onClick={() => onDelete(it.id as unknown as string)} className="px-2 py-1 bg-red-500 text-white rounded">Delete</button>
              </td>
            </tr>
          ))}
        </tbody>
      </table>
    </div>
  );
}
