import React from 'react';
import styled from 'styled-components';
import Image from 'next/image';
import CreateOutlinedIcon from '@mui/icons-material/CreateOutlined';

interface IUserProfileProps {
    user?: {
        profileImg?: string;
    };
}


const UserProfile = ({ user }: IUserProfileProps) => {

    const onEditClick = () => {
        console.log('Edit 클릭!')
    }

    return (
        <Wrapper>
            {user && user.profileImg ? (
                <>
                    <Image
                        src={user.profileImg}
                        alt="User Profile"
                        layout="fill"
                        objectFit="cover"
                        objectPosition="center"
                    />
                    <Edit>
                        <CreateOutlinedIcon style={{ color: '4A4A4A' }} />
                    </Edit>
                </>
            ) : (
                <>
                    <Edit onClick={onEditClick}>
                        <CreateOutlinedIcon style={{ color: '4A4A4A' }} />
                    </Edit>
                </>
            )}
        </Wrapper>
    );
};



const Wrapper = styled.div`
    width: 140px;
    height: 140px;
    border-radius: 50%;
    background-color: ${({ theme }) => theme.colors.light};
    position: relative;
`;

const Edit = styled.div`
    position: absolute;
    top: 0;
    right: 0;
    width: 40px;
    height: 40px;
    background-color: #F5F5F5;
    border-radius: 50%;
    display: flex;
    align-items: center;
    justify-content: center;
    cursor: pointer;
    box-shadow: 0px 0px 10px rgba(0, 0, 0, 0.5);
    svg {
        fill: ${({ theme }) => theme.colors.black80}
    }
`;

const DefaultImage = styled.img`
    width: 100%;
    height: 100%;
    object-fit: cover;
    border-radius: 50%;
`;

export default UserProfile;


