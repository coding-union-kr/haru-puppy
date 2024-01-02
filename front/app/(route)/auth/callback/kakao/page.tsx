'use client'

import React, { useEffect, useState } from 'react';
import axios from 'axios';
import { useRouter, useSearchParams } from 'next/navigation';
import { BACKEND_REDIRECT_URL, LOCAL_STORAGE_KEYS } from '@/app/constants/auth';
import styled from 'styled-components';
import Image from 'next/image';
import Link from "next/link";

const Page = () => {
    const router = useRouter();
    const params = useSearchParams();
    const code = params?.get('code');
    const [error, setError] = useState<string>();


    useEffect(() => {
        const handleLogin = async () => {
            try {
                const response = await axios.get(
                    `${BACKEND_REDIRECT_URL}?code=${code}`,
                );

                if (response) {
                    const accessToken = response.headers['Access-Token'];
                    const refreshToken = response.headers['Refresh-Token'];
                    localStorage.setItem(LOCAL_STORAGE_KEYS.ACCESS_TOKEN, accessToken);
                    localStorage.setItem(LOCAL_STORAGE_KEYS.REFRESH_TOKEN, refreshToken);

                    if (response.status === 200) {
                        router.push('/');
                    } else if (response.status === 404) {
                        router.push('/auth/welcome');
                        console.log('404 response: 카카오 정보', response)
                    }

                } else {
                    setError('로그인을 시도하는 중 오류가 발생했습니다.');
                }
            } catch (error) {
                setError('로그인을 시도하는 중 오류가 발생했습니다.');
            }
        };

        if (code) {
            handleLogin();
        }
    }, [code, router]);

    return (
        <Wrapper>
            <Image width={300} height={300} src='/svgs/dog_profile.svg' alt='dog_profile' />
            <p>{error}</p>

            <StyledLink href="/auth/login">로그인 페이지로 돌아가기</StyledLink>
        </Wrapper>
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