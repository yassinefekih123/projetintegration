"use client";
import React from "react";

type Props = { message: string; onClose?: () => void; closing?: boolean };

export default function Toast({ message, onClose, closing }: Props) {
  const cls = closing ? 'animate-fade-out' : 'animate-fade-in';
  return (
    <div className={`${cls} bg-gray-900 text-white px-4 py-2 rounded shadow flex items-start gap-3`}>
      <div className="flex-1">{message}</div>
      <button aria-label="Dismiss" onClick={onClose} className="toast-close text-white/80 hover:text-white">×</button>
    </div>
  );
}
