"use client";
import React from "react";

type Props = {
  show: boolean;
  title?: string;
  children?: React.ReactNode;
  onConfirm: () => void;
  onCancel: () => void;
};

export default function ConfirmModal({ show, title = "Confirm", children, onConfirm, onCancel }: Props) {
  if (!show) return null;
  return (
    <div className="fixed inset-0 bg-black/40 flex items-center justify-center z-50">
      <div className="bg-white rounded shadow-lg max-w-md w-full p-6">
        <h3 className="text-lg font-semibold mb-2">{title}</h3>
        <div className="mb-4">{children}</div>
        <div className="flex justify-end gap-2">
          <button className="px-3 py-1 bg-gray-200 rounded" onClick={onCancel}>Cancel</button>
          <button className="px-3 py-1 bg-red-600 text-white rounded" onClick={onConfirm}>Confirm</button>
        </div>
      </div>
    </div>
  );
}
