'use client'

import React, { useState } from 'react'
import NavMenu from './components/NavMenu'
import styled from 'styled-components'
import UpperUserProfile from './components/UpperUserProfile'
import ToggleSwitch from '@/app/components/toggle/ToggleSwitch'
import { useMutation, useQueryClient } from 'react-query'
import axios from 'axios'


const UserDummy = {
    nickname: '이로',
    role: '엄마',
    profileImg: '/svgs/user_profile.svg'
}

const page = () => {
    const [isToggled, setIsToggled] = useState(false);
    const queryClient = useQueryClient();

    //알림 설정 fetcher 함수
    const fetchNotification = async (active: boolean, accessToken: string | null) => {
        try {
            const response = await axios.put(
                `/api/notifications?active=${active}`,
                null,
                {
                    headers: {
                        'Content-Type': 'application/json',
                        Authorization: `Bearer ${accessToken}`,
                    },
                }
            );

            if (!response) {
                console.error('알림 설정 요청 실패');
                throw new Error('알림 설정 요청 실패');
            }

            return response.data;
        } catch (error) {
            console.error('알림 설정을 요청하는 중 에러가 발생했습니다:', error);
            throw error;
        }
    };


    const { mutate } = useMutation(
        (active: boolean) => fetchNotification(active, localStorage.getItem('access_token')),
        {
            onSuccess: () => {
                queryClient.invalidateQueries('notifications');
            },
        }
    );

    //토글 함수
    const handelToggle = (toggled: boolean) => {
        setIsToggled(toggled)
        mutate(toggled);
    }

    return (
        <Wrapper>
            <UpperUserProfile user={UserDummy} />
            <MenuWrapper>
                <NavMenu title='알림 설정' >
                    <ToggleSwitch onToggle={handelToggle} isToggled={isToggled} />
                </NavMenu>
                <NavMenu title='로그아웃' />
                <NavMenu title='회원 탈퇴' />
                <NavMenu title='메이트 초대하기' />
            </MenuWrapper>
        </Wrapper>
    )
}


const MenuWrapper = styled.div`
    display: flex;
    flex-direction: column;
    gap: 20px;
`

const Wrapper = styled.div`
    display: flex;  
    flex-direction: column;
    justify-items: center;
    align-items: center;
    margin: 0 auto;
`
export default page

