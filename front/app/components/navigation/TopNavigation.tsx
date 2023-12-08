import React, {useState} from 'react';
import Image from 'next/image';
import ArrowBackRoundedIcon from '@mui/icons-material/ArrowBackRounded';
import NotificationsNoneRoundedIcon from '@mui/icons-material/NotificationsNoneRounded';
import NotificationUnreadIcon from '../../../public/svgs/notifications_unread.svg';
import styled from "styled-components";

const TopNavigation = () => {
    const [hasNotification, setHasNotification] = useState(true);

    const NotiComponent = hasNotification 
    ? <Image src={NotificationUnreadIcon} alt="알림" /> 
    : <NotificationsNoneRoundedIcon />;

    const handleNotiClick = () => {
        setHasNotification(false); 
    };

    const handleGoBack = () =>  window.history.back();
    

   return (
    <TopNavigationWrap>
        <ul>
          <li><button onClick={handleGoBack}><ArrowBackRoundedIcon/></button></li>
          <li><button onClick={handleNotiClick}>{NotiComponent}</button></li>
        </ul>
    </TopNavigationWrap>
   )
};

const TopNavigationWrap = styled.nav`
    position: fixed;
    top: 0;
    width: 390px;
    border-bottom: 0.5px solid ${({theme}) => theme.colors.black60};

    & > ul {
        display: flex;
        justify-content: space-between;
        align-items: center;
        height: 48px;
        padding: 0 15px;
    }

    & button {
      padding: 10px 15px;
      color: ${({theme}) => theme.colors.black80};

    &:hover {
       color: ${({theme}) => theme.colors.black90};
     }
    }
`

  export default TopNavigation;
  