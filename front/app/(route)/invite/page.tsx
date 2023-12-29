"use client"

import Image from 'next/image';
import kakaoLogo from '../../../public/svgs/kakao_logo.svg'
import styled from "styled-components";
import ContainerLayout from '@/app/components/layout/layout';
import TopNavigation from '@/app/components/navigation/TopNavigation';

const InvitePage = () => {
   return (
    <ContainerLayout>
        <TopNavigation/>
        <InvitePageWrap>
            <strong>강아지를 같이 돌볼 메이트를 초대해주세요!</strong>
            <InviteButtonWrap>
            <Image
                priority
                src={kakaoLogo}
                alt="카카오톡"
                width={20}
                height={20}
            />
            카카오톡으로 공유하기
            </InviteButtonWrap>
        </InvitePageWrap>
    </ContainerLayout>
       
   )
};

const InvitePageWrap = styled.div`
    display: flex;
    flex-direction: column;
    align-items: center;
    & > strong {
        font-weight: ${({theme}) => theme.typo.semibold};
    }
`
const InviteButtonWrap = styled.button`
    display: flex;
    justify-content: flex-start;
    align-items: center;
    margin-top: 300px;
    width: 370px;
    height: 56px;
    border-radius: 10px;
    font-size: 18px;
    font-weight: ${({theme}) => theme.typo.regular};
    background-color: #FDDC3F;
    color: #000000;
    & > img {
        margin: 0 28px 0 53px;
    }
`

  export default InvitePage;
