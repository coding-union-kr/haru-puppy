"use client"

import Image from 'next/image';
import kakaoMsgIcon from '@/public/svgs/message-circle.svg'
import styled from "styled-components";
import ContainerLayout from '@/app/components/layout/layout';
import axios from 'axios'
import { useRouter } from 'next/navigation';
import { KAKAO_AUTH_URL } from '@/app/constants/api';

const LoginPage = () => {


    const router = useRouter();
    const onLoginClick = () => {
        router.push(KAKAO_AUTH_URL)
    }

    return (
        <ContainerLayout>
            <main>
                <LoginButtonWrap onClick={onLoginClick}>
                    <Image
                        priority
                        src={kakaoMsgIcon}
                        alt="카카오 메세지"
                    />
                    카카오톡 계정으로 시작
                </LoginButtonWrap>
            </main>
        </ContainerLayout>

    )
};

const LoginButtonWrap = styled.button`
    display: flex;
    justify-content: flex-start;
    align-items: center;
    width: 322px;
    height: 44px;
    border: 1px solid #F2C94C;
    border-radius: 44px;
    font-size: 14px;
    font-weight: ${({ theme }) => theme.typo.regular};
    color: ${({ theme }) => theme.colors.black80};
    & > img {
        margin: 0 59px 0 14px;
    }
`

export default LoginPage;
