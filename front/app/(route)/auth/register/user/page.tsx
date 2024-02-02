'use client';
import { useState, useEffect } from 'react';
import { useRouter, useSearchParams } from 'next/navigation';
import { useRecoilValue } from 'recoil';
import { userState } from '@/app/_states/userState';
import { useMutation } from 'react-query';
import axios from 'axios';
import ProfileImg, { ProfileType } from '@/app/components/profile/ProfileImg';
import Input, { InputType } from '@/app/components/input/Input';
import Button from '@/app/components/button/Button';
import styled from 'styled-components';
import ContainerLayout from '@/app/components/layout/layout';
import TopNavigation from '@/app/components/navigation/TopNavigation';
import { IUser } from '@/app/_types/user/User';
import RoleDropdown from '@/app/components/profile/RoleDropdown';

const UserRegisterPage = () => {
  const searchParams = useSearchParams();

  const userData = useRecoilValue(userState);
  const isInvitedUser = userData.homeId !== '';

  const [formData, setFormData] = useState<IUser>({
    imgUrl: 'src://',
    email: '',
    password: 'password',
    nickName: '',
    userRole: '',
  });

  useEffect(() => {
    const email = searchParams?.get('email');
    if (email) {
      setFormData((prevFormData) => ({ ...prevFormData, email }));
    }
  }, [searchParams]);

  const [isFormIncomplete, setIsFormIncomplete] = useState(true);

  const handleSignupForm = (name: string, value: any) => {
    const newFormData = { ...formData, [name]: value };
    setFormData(newFormData);

    const formIncomplete = newFormData.nickName === '' || newFormData.userRole === '';
    setIsFormIncomplete(formIncomplete);
    console.log(newFormData);
    console.log(formIncomplete);
  };

  const postApi = (data: any) => {
    return axios.post(`/api/users/invitation/${userData.homeId}`, data);
  };

  const mutation = useMutation(postApi, {
    onSuccess: () => {
      router.push('/home');
    },
    onError: (error) => {
      console.error('가입 실패:', error);
    },
  });

  const router = useRouter();
  const handleSubmit = () => {
    const userRequestData = {
      email: formData.email,
      password: formData.password,
      nickName: formData.nickName,
      imgUrl: formData.imgUrl,
      userRole: formData.userRole,
    };
    const requestData = {
      user: userRequestData,
      homeId: userData.homeId,
    };

    if (isInvitedUser) {
      mutation.mutate(requestData);
    } else {
      sessionStorage.setItem('userRequestData', JSON.stringify(userRequestData));
      router.push('/auth/register/dog');
    }
  };

  return (
    <ContainerLayout>
      <TopNavigation />
      <UserProfileFormWrap>
        <ProfileImg profileType={ProfileType.User} onValueChange={(value) => handleSignupForm('imgUrl', value)} />
        <Input inputType={InputType.NickName} onInputValue={(value) => handleSignupForm('nickName', value)} />
        <RoleDropdown onValueChange={(value) => handleSignupForm('userRole', value)} />
        <Button onClick={handleSubmit} disabled={isFormIncomplete}>
          {isInvitedUser ? '가입하기' : '저장하기'}
        </Button>
      </UserProfileFormWrap>
    </ContainerLayout>
  );
};

const UserProfileFormWrap = styled.div`
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

export default UserRegisterPage;
