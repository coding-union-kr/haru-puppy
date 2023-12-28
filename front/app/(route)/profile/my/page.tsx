"use client"
import {useState} from 'react';
import ProfileImg, {ProfileType} from '@/app/components/profile/ProfileImg';
import Input, {InputType} from "@/app/components/input/Input";
import Button from "@/app/components/button/Button";
import styled from "styled-components";
import ContainerLayout from '@/app/components/layout/layout';
import RoleDropdown from './components/RoleDropdown';
import TopNavigation from '@/app/components/navigation/TopNavigation';


const MyProfilePage = () => {
    interface FormData {
       img?: File,
       nickname: string;
       role: string;
    }

    const [formData, setFormData] = useState<FormData>({
        img: undefined,
        nickname: '',
        role: '아빠',
    });
   const handleSignupForm = (name: string, value: any) => {
    const newFormData = {
        ...formData,
        [name]: value
    };

    setFormData(newFormData);
    console.log('업데이트 된 formData:', newFormData);
   }

   const isFormIncomplete = formData.nickname === '' || formData.role === '';

   return (
    <ContainerLayout>
    <TopNavigation/>
    <UserProfileFormWrap>
    <ProfileImg profileType={ProfileType.User} onValueChange={(value)=> handleSignupForm('img', value)}/>
    <Input inputType={InputType.NickName} onInputValue={(value) => handleSignupForm('nickname', value)}/>
    <RoleDropdown  selectedValue={formData.role} onValueChange={(value) => handleSignupForm('role', value)} />
    <Button onClick={() => console.log('api요청 보내는 함수 만들기', formData)} disabled={isFormIncomplete}>저장하기</Button>
    </UserProfileFormWrap>
    </ContainerLayout>
   )
};

const UserProfileFormWrap = styled.form`
    display: flex;
    flex-direction: column;
    align-items: center;
    margin-top: 100px;
    & > div{
        margin-bottom: 45px;
    }

    & > button {
        margin-top: 150px;
    }
`

export default MyProfilePage;
  