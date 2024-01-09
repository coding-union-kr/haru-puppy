'use client'

import Button from '@/app/components/button/Button'
import React from 'react'
import { useRouter, useSearchParams } from 'next/navigation';
import styled from 'styled-components';
import Image from 'next/image';


const page = () => {
    const router = useRouter()
    const searchParams = useSearchParams();
    const email = searchParams?.get('email');
    const onBtnClick = () => {
        router.push(`/auth/register/user?email=${email}`)
    }
    return (
        <Wrapper>
            <p>환영합니다!</p>
            <Image width={300} height={300} src='/svgs/dog-logo.svg' alt='dog_profile' />

            <Button onClick={onBtnClick}>내 프로필 작성하기</Button>
        </Wrapper>
    )
}

const Wrapper = styled.div`
    display: flex;
    flex-direction: column;
    justify-content: center;
    align-items: center;
    margin: 200px auto;
    & p {
        font-size: 24px;
        font-weight:500;
        color: ${({ theme }) => theme.colors.black90}

    }
    & button {
        margin-top: 200px;
    }
    
`

export default page
