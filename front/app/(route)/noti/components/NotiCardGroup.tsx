"use client"

import styled from "styled-components";
import { scheduleTypeOptions } from '../../../constants/scheduleTypeOptions';
import NotiCard from "./NotiCard";

interface INotiData {
    sendDate: string;
    notifications: {
      time: string;
      message: string;
    }[];
}

interface INotiCardGroupProps {
    notiData: INotiData;
}

const NotiCardGroup = ({notiData} :INotiCardGroupProps) => {
 
   return (
   <NotiCardGroupWrap>
     <strong>{notiData.sendDate}</strong>
     <div>
     {notiData.notifications.map((noti, index) => {
          const matchingOption = scheduleTypeOptions.find(option => noti.message.includes(option.label));
          const icon = matchingOption?.icon || null;
          return (
            <NotiCard
              key={index}
              time={noti.time}
              icon={icon}
              message={noti.message}
            />
          );
        })}
     </div>
    </NotiCardGroupWrap>
   )
};

const NotiCardGroupWrap = styled.div`
    width: 340px;
    height: fit-content;
    display: flex;
    flex-direction: column;
    gap: 16px;

    & > div {
        display: flex;
        flex-direction: column;
        gap: 16px;
    }

    & > strong {
        color: ${({ theme }) => theme.colors.black80};
        font-weight: ${({ theme }) => theme.typo.semibold};
        font-size: 16px;
    }
`

  export default NotiCardGroup;
  