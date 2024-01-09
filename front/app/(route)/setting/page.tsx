'use client'

import React, { useState } from 'react'
import NavMenu from './components/NavMenu'
import styled from 'styled-components'
import UpperUserProfile from './components/UpperUserProfile'
import ToggleSwitch from '@/app/components/toggle/ToggleSwitch'
import { useMutation, useQueryClient } from 'react-query'
import axios from 'axios'
import ContainerLayout from '@/app/components/layout/layout'
import TopNavigation from '@/app/components/navigation/TopNavigation'
import { LOCAL_STORAGE_KEYS } from '@/app/constants/auth'
import { useRouter } from 'next/navigation'
import Modal from '@/app/components/modal/modal'
import BottomNavigation from '@/app/components/navigation/BottomNavigation'


const UserDummy = {
    userId: '11111',
    nickname: '이로',
    role: '엄마',
    profileImg: '/svgs/user_profile.svg'
}

const page = () => {
    const [isToggled, setIsToggled] = useState(false);
    const [isLogoutModal, setLogoutIsModal] = useState(false);
    const [isTerminateModal, setTerminateModal] = useState(false);

    const queryClient = useQueryClient();
    const router = useRouter();


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


    const { mutate: notification } = useMutation(
        (active: boolean) => fetchNotification(active, localStorage.getItem(LOCAL_STORAGE_KEYS.ACCESS_TOKEN)),
        {
            onSuccess: () => {
                queryClient.invalidateQueries('notifications');
            },
        }
    );

    //알림 토글 함수
    const handelToggle = (toggled: boolean) => {
        setIsToggled(toggled)
        notification(toggled);
    }

    //매이트 초대 함수(/invite로 라우팅)
    const handleMateInvite = () => {
        router.push('/invite')
    }


    const toggleLogoutModal = () => {
        setLogoutIsModal(!isLogoutModal)
    }

    const toggleTerminateModal = () => {
        setTerminateModal(!isTerminateModal)
    }


    //로그아웃
    const handelLogout = () => {
        localStorage.removeItem(LOCAL_STORAGE_KEYS.ACCESS_TOKEN);
        localStorage.removeItem(LOCAL_STORAGE_KEYS.REFRESH_TOKEN);
        router.push('/auth/login');
    }


    //회원탈퇴
    const { mutate: terminateMutation } = useMutation(
        async ({ userId, accessToken }: { userId: string; accessToken: string | null }) => {
            const response = await axios.delete(`/api/users/${userId}`, {
                headers: {
                    'Content-Type': 'application/json',
                    Authorization: `Bearer ${accessToken}`,
                },
            });

            return response.data;
        },
        {
            onSuccess: (data) => {
                localStorage.removeItem(LOCAL_STORAGE_KEYS.ACCESS_TOKEN);
                localStorage.removeItem(LOCAL_STORAGE_KEYS.REFRESH_TOKEN);
                router.push('/auth/login');
            },
            onError: (error) => {
                console.error('회원 탈퇴 에러:', error);
            },
        }
    );

    //회원 탈퇴 
    const handleTerminate = () => {
        const userId = UserDummy.userId;
        const accessToken = localStorage.getItem('access_token');
        terminateMutation({ userId, accessToken })

    };


    return (
        <>
            <TopNavigation />
            <Wrapper>
                <UpperUserProfile user={UserDummy} />
                <MenuWrapper>
                    <NavMenu title='알림 설정' >
                        <ToggleSwitch onToggle={handelToggle} isToggled={isToggled} />
                    </NavMenu>
                    <NavMenu title='로그아웃' onClick={toggleLogoutModal} />
                    {isLogoutModal && (
                        <Modal
                            children="로그아웃하시겠습니까?"
                            btn1="취소"
                            btn2="로그아웃"
                            onClose={toggleLogoutModal}
                        />
                    )}
                    <NavMenu title='회원 탈퇴' onClick={toggleTerminateModal} />
                    {isTerminateModal && (
                        <Modal
                            children="정말 탈퇴 하시겠습니까?"
                            btn1="취소"
                            btn2="회원 탈퇴"
                            onClose={toggleTerminateModal}
                        />
                    )}
                    <NavMenu title='메이트 초대하기' onClick={handleMateInvite} />
                </MenuWrapper>
            </Wrapper>
            <BottomNavigation />
        </>
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
   margin-top: 50px;
`
export default page

