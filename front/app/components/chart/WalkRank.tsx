import React from 'react';
import styled from 'styled-components';

interface IUser {
  username: string;
  profileImg: string;
  walkCount: number;
}

interface IWalkRank {
  users: IUser[];
}

const WalkRank = ({ users }: IWalkRank) => {
  return (
    <Wrapper>
      {users?.map((user, index) => (
        <BoxWrapper key={index}>
          <UserContainer>
            <UserProfileImage src={user.profileImg} alt={`${user.username}의 프로필 사진`} />
            <Nickname>{user.username}</Nickname>
            <WalkCount>{user.walkCount}회</WalkCount>
          </UserContainer>
          <Bar walkCount={user.walkCount} />
        </BoxWrapper>
      ))}
    </Wrapper>
  );
};

export default WalkRank;



const Wrapper = styled.div`
  display: flex;
  justify-content: space-between;
  width: 300px;
  height: 500px;
`;

const BoxWrapper = styled.div`
  width: 100px;
  height: 250px;
  display: flex;
  margin-right: 12px;
  flex-direction: column;
  position: relative;
`;

const UserContainer = styled.div`
  display: flex;
  flex-direction: column;
  align-items: center;
  margin: 0 10px;
`;

const Bar = styled.div<{ walkCount: number }>`
  width: 80px;
  height: ${({ walkCount }) => walkCount * 20}px;
  background-color: ${({ theme, walkCount }) => {
    const maxWalkCount = 18;
    const intensity = walkCount / maxWalkCount;
    const grayShade = Math.round(255 - intensity * 255);
    return `rgb(${grayShade}, ${grayShade}, ${grayShade})`;
  }};
  border-radius: 10px;
  position: absolute;
  bottom: 0;
  left: 50%;
  transform: translateX(-50%);
`;

const WalkCount = styled.span`
  font-weight: bold;
  margin-top: 5px;
  font-weight: ${({ theme }) => theme.typo.medium};
`;

const Nickname = styled.span`
  margin-top: 5px;
`;

const UserProfileImage = styled.img`
  width: 65px;
  height: 65px;
  border-radius: 50%;
  margin-top: 10px;
`;
