import React from 'react'
import styled from 'styled-components'
import Image from 'next/image'
import ArrowForwardIosRoundedIcon from '@mui/icons-material/ArrowForwardIosRounded';
import { StyledArrowIcon } from './NavMenu';
import { useRouter } from 'next/navigation';


interface IUserInfo {
    nickname: string;
    role: string;
    profileImg: string;
}

interface IUpperUserProfile {
    user: IUserInfo
}

const UpperUserProfile = ({ user }: IUpperUserProfile) => {

    const router = useRouter()
    const onUserProfileClick = () => {
        router.push('/profile/my')
    }
    return (
        <Wrapper onClick={onUserProfileClick}>
            {user.profileImg ? (
                <Image
                    src={user.profileImg}
                    alt="User Profile"
                    width={70}
                    height={70}
                />
            ) : (
                <Image
                    src="/svgs/user_profile.svg"
                    alt="Default User Profile"
                    width={70}
                    height={70}
                />
            )}
            <InfoWrapper>
                <Info>
                    <div>
                        <span>{user.nickname}</span>
                        <span>{user.role}</span>
                    </div>
                    <div><StyledArrowIcon /></div>
                </Info>
            </InfoWrapper>

        </Wrapper>
    )
}


const InfoWrapper = styled.div`
    display: flex;
    justify-content: space-between;
    width: 70%;
    cursor: pointer;
`;

const Info = styled.div`
    display: flex;
    justify-content: space-between;
    align-items: center;
    width: 100%;

    & > div:first-child {
        display: flex;
        flex-direction: column;
        span:nth-child(1) {
            display: inline-block;
            font-size: 24px;
            font-weight: ${({ theme }) => theme.typo.regular};
            color: ${({ theme }) => theme.colors.black90}; 
            margin-bottom: 8px;
        }

        span:nth-child(2) {
            display: inline-block;
            font-size: 14px;
            color: ${({ theme }) => theme.colors.black70};
        }
    }

    & > div {
        display: flex;
        align-items: center;
    }
`;

const Wrapper = styled.div`
    justify-content: space-between;
    width: 340px;
    height: 70px;
    display: flex;
    margin: 50px 0px;
`;


export default UpperUserProfile