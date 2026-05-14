"use client";

import React from 'react';

export default function Pagination({ page, size, total, onPage }: { page: number; size: number; total: number; onPage: (p: number) => void; }) {
  const totalPages = Math.max(1, Math.ceil(total / size));
  return (
    <div className="flex items-center justify-between mt-4">
      <div className="text-sm text-gray-600">Page {page + 1} of {totalPages}</div>
      <div className="flex gap-2">
        <button disabled={page <= 0} onClick={() => onPage(page - 1)} className="px-3 py-1 border rounded">Previous</button>
        <button disabled={page >= totalPages - 1} onClick={() => onPage(page + 1)} className="px-3 py-1 border rounded">Next</button>
      </div>
    </div>
  );
}
