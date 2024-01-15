"use client"
import { useRouter } from 'next/navigation';
import { LOCAL_STORAGE_KEYS } from '@/app/constants/api';

export default function Home() {
  const router = useRouter();
  const token = localStorage.getItem(LOCAL_STORAGE_KEYS.ACCESS_TOKEN);

  if (token) {
    router.push('/home');
  } else {
    router.push('/auth/login');
  }

}


