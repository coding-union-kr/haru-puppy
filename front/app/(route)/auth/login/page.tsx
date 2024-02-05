'use client';
import { useRecoilState } from 'recoil';
import { userState } from '@/app/_states/userState';
import Image from 'next/image';
import kakaoMsgIcon from '@/public/svgs/message-circle.svg';
import styled from 'styled-components';
import ContainerLayout from '@/app/components/layout/layout';
import { useRouter, useSearchParams } from 'next/navigation';
import { KAKAO_AUTH_URL } from '@/app/constants/api';
import { useEffect } from 'react';

const LoginPage = () => {
  const [, setUserData] = useRecoilState(userState);
  const router = useRouter();
  const params = useSearchParams();

  useEffect(() => {
    const homeId = params?.get('homeId') || null;
    if (homeId) {
      setUserData((prevUserData) => ({
        ...prevUserData,
        homeId: homeId,
      }));
    }
  }, [setUserData, params]);

  const onLoginClick = () => {
    router.push(KAKAO_AUTH_URL);
  };

  return (
    <ContainerLayout>
      <main>
        <LoginButtonWrap onClick={onLoginClick}>
          <Image priority src={kakaoMsgIcon} alt='카카오 메세지' />
          카카오톡 계정으로 시작
        </LoginButtonWrap>
      </main>
    </ContainerLayout>
  );
};

const LoginButtonWrap = styled.button`
  display: flex;
  justify-content: flex-start;
  align-items: center;
  width: 322px;
  height: 44px;
  border: 1px solid #f2c94c;
  border-radius: 44px;
  font-size: 14px;
  font-weight: ${({ theme }) => theme.typo.regular};
  color: ${({ theme }) => theme.colors.black80};
  & > img {
    margin: 0 59px 0 14px;
  }
`;

export default LoginPage;
