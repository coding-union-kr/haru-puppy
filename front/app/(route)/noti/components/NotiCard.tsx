"use client"

import styled from "styled-components";

interface INotiCardProps {
    icon: JSX.Element | null;
    message: string;
    time: string;
}

const NotiCard = ({time, icon, message} :INotiCardProps) => {
   return (
   <NotiCardWrap>
      <div>{icon}</div>
      <p>{message}</p>
      <span>{time}</span>
    </NotiCardWrap>
   )
};

const NotiCardWrap = styled.div`
     position: relative;
     display: flex;
     align-items: center;
     height: 76px;
     border-radius: 10px;
     background-color: #ffffff;   
     padding-left: 22px;

     & > p {
        margin-left: 10px;
        color: ${({ theme }) => theme.colors.black90};
        font-weight: ${({ theme }) => theme.typo.medium};
        font-size: 16px;
     }

     & > span {
        position: absolute;
        right: 10px;
        bottom: 10px;
        color: ${({ theme }) => theme.colors.black80};
        font-weight: ${({ theme }) => theme.typo.regular};
        font-size: 12px;
     }
`

  export default NotiCard;
  