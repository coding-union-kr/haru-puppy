import React, { useState } from 'react';
import styled from 'styled-components';

interface MateProfileProps {
    clickable?: boolean;
}

const MateProfile = ({ clickable = true }: MateProfileProps) => {
    const [isProfileClicked, setProfileClicked] = useState(false);

    const onProfileClick = () => {
        if (clickable) {
            console.log('메이트 프로필 클릭');
            setProfileClicked(!isProfileClicked);
        }
    };

    return (
        <Wrapper>
            <ProfileContainer>
                <Profile onClick={onProfileClick} isClicked={isProfileClicked} clickable={clickable} />
            </ProfileContainer>
            <Info>
                <NickName>닉네임</NickName>
                <Name>엄마</Name>
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
  height: 40px;
`;

const Profile = styled.div<{ isClicked: boolean; clickable: boolean }>`
  width: 40px;
  height: 40px;
  border-radius: 50%;
  background-color: ${({ theme }) => theme.colors.light};
  position: relative;
  cursor: ${({ clickable }) => (clickable ? 'pointer' : 'default')};
  box-sizing: border-box;
  border: ${({ isClicked }) => (isClicked ? '2px solid #06acf4' : 'none')};
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