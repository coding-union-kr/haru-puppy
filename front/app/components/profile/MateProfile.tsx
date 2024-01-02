import { Imates } from '@/app/(route)/schedule/components/ScheduleAdd';
import React from 'react';
import styled from 'styled-components';
import Image from 'next/image';

interface IMateProfileProps {
  isClicked: boolean;
  onClick: (userId: string) => void;
  mate: Imates
}

const MateProfile = ({ isClicked, onClick, mate }: IMateProfileProps) => {
  return (
    <Wrapper>
      <ProfileContainer>
        <Profile isClicked={isClicked} onClick={() => onClick(mate.user_id)} />
        {isClicked &&
          <Image src='/svgs/mate_check.svg' alt='mate-check' width={20} height={20} />
        }
      </ProfileContainer>
      <Info>
        <NickName>{mate.nickname}</NickName>
        <Name>{mate.role}</Name>
      </Info>
    </Wrapper>
  );
};


const Wrapper = styled.div`
  width: 80px;
  height: 100px;
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
`;

const ProfileContainer = styled.div`
position: relative;
  height: 40px;
  & > img {
    position: absolute;
    top: 20%;
    left: 85%;
    transform: translate(-50%, -50%);
    z-index: 2;
  }
`;

const Profile = styled.div<{ isClicked: boolean }>`
  width: 40px;
  height: 40px;
  border-radius: 50%;
  background-color: ${({ theme }) => theme.colors.light};
  position: relative;
  box-sizing: border-box;
  border: ${({ isClicked }) => (isClicked ? '2px solid #06acf4' : 'none')};
  cursor: pointer;
`;

const Info = styled.div`
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
  p {
    display: inline-block;
    margin: 3px;
  }
`;

const NickName = styled.p`
  font-size: 14px;
  color: ${({ theme }) => theme.colors.black90};
`;

const Name = styled.p`
  font-size: 12px;
  color: ${({ theme }) => theme.colors.black80};
`;

export default MateProfile;
