"use client"

import styled from "styled-components";
import ContainerLayout from "@/app/components/layout/layout";
import TopNavigation from "@/app/components/navigation/TopNavigation";
import NotiCardGroup from "./components/NotiCardGroup";

const dummyNotiData = [
    {
      sendDate: "2024-01-01",
      notifications: [
        {
          time: '02:43 pm',
          message: '5분 후에 산책 시간이에요!'
        },
        {
            time: '02:43 pm',
            message: '10분 후에 밥 주기 시간이에요!'
        },
      ]
    },
    {
        sendDate: "2023-12-31",
        notifications: [
          {
            time: '02:43 pm',
            message: '1시간 후에 물 교체 시간이에요!'
          },
          {
              time: '02:43 pm',
              message: '30분 후에 미용 시간이에요!'
          },
        ]
      },
  ];

const NotiPage = () => {
   return (
    <ContainerLayout>
        <TopNavigation/>
        <NotiPageWrap>
        {dummyNotiData.map((notiGroup, index) => (
          <>
            <NotiCardGroup key={index} notiData={notiGroup} />
            {index < dummyNotiData.length - 1 && <Divider />}
          </>
        ))}
        </NotiPageWrap>
    </ContainerLayout> 
   )
};

const NotiPageWrap = styled.div`
    width: 100%;
    height: 100%;
    display: flex;
    flex-direction: column;
    justify-content: center;
    align-items: center;
    gap: 8px;
    margin-top: 64px;
    background-color: ${({ theme }) => theme.colors.background};
`

const Divider = styled.hr`
  width: 2px;
  height: 30px; 
  border: 0;
  background-color: ${({ theme }) => theme.colors.black60};
  margin-left: 56px;
`;

  export default NotiPage;
