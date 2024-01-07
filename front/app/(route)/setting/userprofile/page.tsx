'use client'

import React, { useState } from 'react';
import ProfileImg, { ProfileType } from '@/app/components/profile/ProfileImg';
import Input, { InputType } from "@/app/components/input/Input";
import Button from "@/app/components/button/Button";
import styled from "styled-components";
import ContainerLayout from '@/app/components/layout/layout';
import RoleDropdown from '../../profile/my/components/RoleDropdown';
import axios from 'axios';
import { useMutation, useQueryClient } from 'react-query';
import { LOCAL_STORAGE_KEYS } from '@/app/constants/auth';
import TopNavigation from '@/app/components/navigation/TopNavigation';
import BottomNavigation from '@/app/components/navigation/BottomNavigation';

interface FormData {
    img?: File;
    nickname: string;
    role: string;
}


const page = () => {
    const queryClient = useQueryClient();

    const [formData, setFormData] = useState<FormData>({
        img: undefined,
        nickname: '',
        role: '',
    });

    const handleSignupForm = (name: keyof FormData, value: any) => {
        const newFormData = {
            ...formData,
            [name]: value
        };

        setFormData(newFormData);
    };

    const saveUserFetcher = async (Data: FormData) => {
        const accessToken = localStorage.getItem(LOCAL_STORAGE_KEYS.ACCESS_TOKEN)

        const response = await axios.post('/api/users/profile', Data, {
            headers: {
                'Content-Type': 'application/json',
                'Authorization': `Bearer ${accessToken}`
            }
        });

        return response.data;
    };

    const { data, mutate: saveUser, isLoading } = useMutation(
        () => saveUserFetcher(formData), {
        onSuccess: () => {
            //key값이 'userProfile' 데이터 최신 값으로 유지
            queryClient.invalidateQueries('userProfile');
            console.log('수정된 userData:', data)
        },
        onError: (error) => {
            console.error('유저 프로필 수정 에러:', error);
        },
    });

    return (
        <ContainerLayout>
            <TopNavigation />
            <UserProfileFormWrap>
                <ProfileImg profileType={ProfileType.User} onValueChange={(value) => handleSignupForm('img', value)} />
                <Input inputType={InputType.NickName} onInputValue={(value) => handleSignupForm('nickname', value)} />
                <RoleDropdown selectedValue={formData.role} onValueChange={(value) => handleSignupForm('role', value)} />
                <Button onClick={saveUser}>저장하기</Button>
            </UserProfileFormWrap>
            <BottomNavigation />
        </ContainerLayout>
    );
};



const UserProfileFormWrap = styled.form`
    display: flex;
    flex-direction: column;
    align-items: center;
    margin-top: 100px;
    & > div {
        margin-bottom: 45px;
    }

    & > button {
        margin-top: 150px;
    }
`;
export default page;