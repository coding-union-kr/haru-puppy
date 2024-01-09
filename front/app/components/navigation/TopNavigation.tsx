import React, { useState, useEffect } from 'react';
import { useRouter, usePathname } from 'next/navigation';
import Image from 'next/image';
import ArrowBackRoundedIcon from '@mui/icons-material/ArrowBackRounded';
import NotificationsNoneRoundedIcon from '@mui/icons-material/NotificationsNoneRounded';
import NotificationUnreadIcon from '../../../public/svgs/notifications_unread.svg';
import styled from "styled-components";

const TopNavigation = () => {
  const token = typeof window !== 'undefined' ? localStorage.getItem('token') : null;
  const [showBtns, setShowBtns] = useState(!!token);

  const [hasNotification, setHasNotification] = useState(true);

  const NotiComponent = hasNotification
    ? <Image src={NotificationUnreadIcon} alt="알림" />
    : <NotificationsNoneRoundedIcon />;

  const handleNotiClick = () => {
    setHasNotification(false);
  };

  useEffect(() => {
    const token = localStorage.getItem('token');
    setShowBtns(!!token);
  }, []);

  const pathname = usePathname();
  const getTitle = (pathname: string | null) => {
    switch (pathname) {
      case '/':
      default:
        return '홈';
      case '/auth/signup':
        return '회원가입';
      case '/auth/login':
        return '로그인';
      case '/schedule':
        return '일정';
      case '/noti':
        return '알림';
      case '/profile/dog':
        return '강아지 프로필';
      case '/profile/my':
        return '내 프로필';
      case '/auth/register/user':
        return '내 프로필';
      case '/auth/register/dog':
        return '강아지 프로필';
      case '/invite':
        return '메이트 초대하기';
      case '/setting':
        return '설정';
      case '/auth/userprofile':
        return '내 프로필';
      case '/setting/userprofile':
        return '내 프로필'
    }
  };

  const initialTitle = getTitle(pathname);
  const [currentTitle, setCurrentTitle] = useState(initialTitle);

  const router = useRouter();
  const handleGoBack = () => router.back();

  useEffect(() => {
    if (pathname) {
      const title = getTitle(pathname);
      setCurrentTitle(title);
    }
  }, [pathname]);

  return (
    <TopNavigationWrap showBtns={showBtns}>
      <button onClick={handleGoBack}><ArrowBackRoundedIcon /></button>
      <strong>{currentTitle}</strong>
      <button onClick={handleNotiClick}>{NotiComponent}</button>
    </TopNavigationWrap>
  )

};

const TopNavigationWrap = styled.nav<{ showBtns: boolean }>`
    position: fixed;
    top: 0;
    z-index: 100;
    background-color: #FFFFFF;
    left: 50%;
    transform: translateX(-50%);
    display: flex;
    align-items: center;
    justify-content: space-between;
    width: 390px; 
    height: 48px;
    border-bottom: 0.5px solid ${({ theme }) => theme.colors.black60};
    background-color: #ffffff;

    & > strong {
        font-size: 16px;
        font-weight: ${({ theme }) => theme.typo.semibold};
    }

    & > button {
        padding: 12px 15px;
        color: ${({ theme }) => theme.colors.black80};
        visibility: ${({ showBtns }) => (showBtns ? 'visible' : 'hidden')};

        &:hover {
            color: ${({ theme }) => theme.colors.black90};
        }
    }
`;

export default TopNavigation;
