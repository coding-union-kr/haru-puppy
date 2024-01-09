import Image from 'next/image';
import React from 'react';
import styled from 'styled-components';

interface IUser {
  user_id: number;
  user_img?: string;
  nickname?: string;
  rank: number;
}

interface IWalkRank {
  ranking: IUser[];
}


const WalkRank = ({ ranking }: IWalkRank) => {
  return (
    <>
      <Wrapper>
        <Title>주간 산책 메이트 랭킹</Title>
        <ChartWrapper>
          {ranking?.map((user, index) => (
            <BoxWrapper key={index}>
              <UserContainer walkCount={user.rank}>
                {user.user_img ? <UserProfileImage><Image src={user.user_img} alt='프로필 이미지' width={65} height={65} /></UserProfileImage>
                  : <UserProfileImage />}
                <Nickname>{user.nickname}</Nickname>
                <WalkCount>{user.rank}회</WalkCount>
              </UserContainer>
              <Bar walkCount={user.rank} />
            </BoxWrapper>
          ))}
        </ChartWrapper>
      </Wrapper>
    </>
  );
};


const Wrapper = styled.div`
  display: flex;
  flex-direction: column;  
`

const ChartWrapper = styled.div`
  display: flex;
  justify-content: center;
  align-items: flex-end;
  width: 370px;
  bottom: 0px;
`;

const Title = styled.span`
  font-size: 20px;
  text-align: start;
  margin-bottom: 20px;
`;
const BoxWrapper = styled.div`
  width: 100px;
  display: flex;
  margin-right: 12px;
  flex-direction: column;
  position: relative;
  
`;

const UserContainer = styled.div<{ walkCount: number }>`
  display: flex;
  flex-direction: column;
  align-items: center;
  margin: 0 10px;
  height: 100%;
  padding-bottom: ${({ walkCount }) => walkCount * 30}px;
`;


const Bar = styled.div<{ walkCount: number }>`
  width: 80px;
  height: ${({ walkCount }) => walkCount * 25}px;
  background-color: ${({ theme, walkCount }) => `rgba(0, 0, 0, ${walkCount / 15})`};
  border-radius: 10px;
  position: absolute;
  bottom: 0;
  left: 50%;
  transform: translateX(-50%);
`

const WalkCount = styled.span`
  font-weight: bold;
  margin-top: 10px;
  font-weight: ${({ theme }) => theme.typo.medium};
`

const Nickname = styled.span`
  margin-top: 10px;
`

const UserProfileImage = styled.div`
  width: 65px;
  height: 65px;
  border-radius: 50%;
  margin-top: 10px ;
  background-color:#CEDBEA;
`;

export default WalkRank;


