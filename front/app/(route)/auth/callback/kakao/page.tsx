'use client'
import React, { useState } from 'react';
import axios from 'axios';
import { useRouter, useSearchParams } from 'next/navigation';
import { BACKEND_REDIRECT_URL, LOCAL_STORAGE_KEYS } from '@/app/constants/api';
import styled from 'styled-components';
import Image from 'next/image';
import Link from "next/link";
import { useQuery } from 'react-query';
import Loading from '@/app/components/loading/loading';


const Page = () => {
    const router = useRouter();
    const params = useSearchParams();
    const code = params?.get('code') || null;
    const [error, setError] = useState<string>();

    const fetcher = async (code: string | null) => {
        if (!code) return null;
        const res = await axios.get(`${BACKEND_REDIRECT_URL}?code=${code}`);
        return res.data.data;
    };

    const { isLoading } = useQuery(['login'], () => fetcher(code), {
        enabled: !!code,
        onSuccess: (responseData: any) => {
            if (responseData.response.isAlreadyRegistered === false) {
                const email = responseData.response.email;
                router.push(`/auth/welcome/?email=${email}`);

            } else {
                const accessToken = responseData.accessToken;
                const refreshToken = responseData.refreshToken;

                localStorage.setItem(LOCAL_STORAGE_KEYS.ACCESS_TOKEN, accessToken);
                localStorage.setItem(LOCAL_STORAGE_KEYS.REFRESH_TOKEN, refreshToken);

                router.push('/');
            }
        },
        onError: (error) => {
            setError('로그인 중 에러가 발생하였습니다.');
            // console.log('error', error)
        },
    });
    if (error) {
        return (
            <Wrapper>
                <Image width={300} height={300} src='/svgs/dog_profile.svg' alt='dog_profile' />
                <StyledLink href="/auth/login">로그인 페이지로 돌아가기</StyledLink>
                <p>{error}</p>
            </Wrapper>
        )
    }

    return (
        <>
            {isLoading &&
                <Loading />
            }
        </>
    );
}

const Wrapper = styled.div`
    display: flex;
    margin: 50px 0px;
    flex-direction: column;
    justify-content: center;
    align-items: center;
    width: 100%;
    height: 100%;
    svg {
        color: ${({ theme }) => theme.colors.light}
    }
   
    & p {
        margin: 100px;
        font-size: 35px;
        font-weight: 400;
        color: ${({ theme }) => theme.colors.black80}; 
    } 
`;
const StyledLink = styled(Link)`
    text-decoration: none;
    color: ${({ theme }) => theme.colors.main};
    border-radius: 50%;
    padding: 15px 30px;  
`;
export default Page;
