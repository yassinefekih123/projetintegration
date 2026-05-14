"use client";

import React, { useEffect } from 'react';
import { useRouter } from 'next/navigation';
import { getToken } from '@/lib/auth';
import Layout from '@/components/Layout';

export default function DashboardPage() {
  const router = useRouter();

  useEffect(() => {
    const token = getToken();
    if (!token) router.push('/login');
  }, [router]);

  return (
    <Layout>
      <div>
        <h1 className="text-2xl font-semibold">Dashboard</h1>
        <p className="mt-4 text-gray-600">Welcome to the Accessories Manager dashboard. Build charts and statistics here.</p>
      </div>
    </Layout>
  );
}
