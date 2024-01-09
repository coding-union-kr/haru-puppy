import Image from 'next/image'
import React from 'react'
import styled from 'styled-components';

export interface IUserProfileProps {
  user?: {
    profileImg?: string;
  };
}

const UserProfile = ({ user }: IUserProfileProps) => {
  return (
    <Wrapper>
      <ImageWrapper>
        {user && user.profileImg ? (
          <>
            <Image
              src={user.profileImg}
              alt="User Profile"
              layout="fill"
              objectFit="cover"
              objectPosition="center"
            />

          </>
        ) : (
          <>
            <Image
              src={'/svgs/dog_profile.svg'}
              alt="User Profile"
              layout="fill"
              objectFit="cover"
              objectPosition="center"
            />
          </>
        )}
      </ImageWrapper>
      <UserInfo>
        <p>멍순이</p>
        <div>
          <span>성별</span> 여자
        </div>
        <div>
          <span>생일</span> 22.10.23
        </div>
        <div>
          <span>체중</span> 4.3 kg
        </div>
      </UserInfo>
      <EditBtn>
        <Image src={'/svgs/home_edit_btn.svg'} alt='mate-edit-btn' width={40} height={40} />
      </EditBtn>
    </Wrapper>
  )
}


const Wrapper = styled.div`
  width: 370px;
  height: 140px;
  display: flex;
  justify-content: flex-end;

`

const UserInfo = styled.div`
display: flex;
flex-direction: column;
width: 130px;
height: 140px;
justify-content: center;
align-items: flex-start;
padding-left: 30px;
    color: ${({ theme }) => theme.colors.black90};

    > p {
      font-size: 24px;
      margin-bottom: 15px;
    }

  > div {
    font-weight: 350;
    margin-top: 10px;
    > span {
      margin-right: 15px;
    }
  }
`

const EditBtn = styled.div`
  display: flex;
  align-items: flex-start;
  justify-content: flex-end;
  cursor: pointer;
`

const ImageWrapper = styled.div`
    width: 140px;
    height: 140px;
    border-radius: 50%;
    background-color: ${({ theme }) => theme.colors.light};
    position: relative;
    > img {
      width: 140px;
      height: 140px;
    
    }
`;


export default UserProfile
