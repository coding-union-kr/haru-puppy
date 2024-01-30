"use client"
import styled from "styled-components";
import ContainerLayout from '@/app/components/layout/layout'
import TopNavigation from '@/app/components/navigation/TopNavigation'
import UserProfile from '@/app/(route)/home/components/UserProfile'
import MateList from '@/app/(route)/home/components/MateList'
import ReportCard from '@/app/(route)/home/components/ReportCard'
import WalkRank from '@/app/(route)/home/components/WalkRank'
import BottomNavigation from '@/app/components/navigation/BottomNavigation'
import { useRouter } from 'next/navigation';
import { useEffect } from 'react';
import axios from "axios";
import { useQuery } from "react-query";
import instance from "@/app/_utils/apis/interceptors";

export const dummyMatesData = [
    {
        user_id: '2222',
        user_img: 'image_url',
        nickname: '파파',
        role: '아빠',
    },
    {
        user_id: '3333',
        user_img: 'image_url',
        nickname: '브라더',
        role: '형',
    },
    {
        user_id: '3333',
        user_img: 'image_url',
        nickname: '브라더',
        role: '형',
    }
]


export const dummyReports = {
    today_poo_cnt: 2,
    last_week_walk_cnt: 7,
    last_wash_date: '2023-12-13 00:00:00',
    last_hospital_date: '2023-12-13 00:00:00'
}


export const dummyRanking = [
    {
        user_id: 1,
        user_img: '',
        nickname: 'User1',
        rank: 4,
    },
    {
        user_id: 2,
        user_img: '',
        nickname: 'User2',
        rank: 2,
    },
    {
        user_id: 3,
        user_img: '',
        nickname: 'User3',
        rank: 3,
    },
];


const fetchHomeData = async () => {
    try {
        const response = await instance.put('/api/home/');
        return response.data;
    } catch (error) {
        throw new Error('Failed to fetch home data');
    }
};



const Page = () => {
    const router = useRouter();
    const { data, isLoading, isError } = useQuery('homeData', fetchHomeData);

    useEffect(() => {
        const accessToken = localStorage.getItem('access_token');
        if (!accessToken) {
            router.push('/auth/login');
        }
    }, []);


    return (
        <main>
            <ContainerLayout>
                <TopNavigation />
                <Wrapper>
                    <UserProfile />
                    <MateList mates={dummyMatesData} />
                    <ReportCard dummyReports={dummyReports} />
                    <WalkRank ranking={dummyRanking} />
                </Wrapper>
                <BottomNavigation />
            </ContainerLayout>
        </main>
    )

}


const Wrapper = styled.div`
  flex: 1; 
  overflow-y: auto; 
  display: flex;
  padding: 100px 20px;
  flex-direction: column;
  justify-content: center;
  align-items: center;
  gap: 50px;
  margin-top: 300px;
`;


export default Page

