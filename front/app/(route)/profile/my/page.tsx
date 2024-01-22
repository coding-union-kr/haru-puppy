'use client';
import { useState, useEffect } from 'react';
import { usePersistentRecoilState } from '@/app/_hooks/usePersistentRecoilState';
import { userState } from '@/app/_states/userState';
import axios from 'axios';
import { useMutation } from 'react-query';
import { LOCAL_STORAGE_KEYS } from '@/app/constants/api';
import ProfileImg, { ProfileType } from '@/app/components/profile/ProfileImg';
import Input, { InputType } from '@/app/components/input/Input';
import Button from '@/app/components/button/Button';
import styled from 'styled-components';
import ContainerLayout from '@/app/components/layout/layout';
import TopNavigation from '@/app/components/navigation/TopNavigation';
import RoleDropdown from '@/app/components/profile/RoleDropdown';
import Modal from '@/app/components/modal/modal';

interface IUserProfileUpdateRequest {
  userId: number;
  imgUrl?: string;
  nickname: string;
  userRole: string;
}

const MyProfilePage = () => {
  const [formData, setFormData] = useState<IUserProfileUpdateRequest>({
    userId: 0,
    imgUrl: 'src://',
    nickname: '',
    userRole: '',
  });

  const [userData, setUserData] = usePersistentRecoilState<any>('userState', userState);

  useEffect(() => {
    if (userData) {
      setFormData((currentFormData) => ({
        ...currentFormData,
        userId: userData.userId,
        nickname: userData.nickname,
        userRole: userData.userRole,
      }));
    }
  }, [userData]);

  console.log('recoil로 업데이트하고 로컬스토리지에서 갖고와서 set한 formData:', formData);
  const handleSignupForm = (name: string, value: any) => {
    const newFormData = {
      ...formData,
      [name]: value,
    };
    setFormData(newFormData);
  };

  const isFormIncomplete = formData.nickname === '' || formData.userRole === '';

  const updateProfile = (formData: IUserProfileUpdateRequest) => {
    const token = localStorage.getItem(LOCAL_STORAGE_KEYS.ACCESS_TOKEN);
    return axios.put('http://localhost:8080/api/users/profile', formData, {
      headers: {
        Authorization: `Bearer ${token}`,
      },
    });
  };

  const mutation = useMutation(updateProfile, {
    onSuccess: (res) => {
      const resData = res.data.data;
      console.log('프로필 업데이트 성공:', resData);
      setUserData(resData);
      setIsModalVisible(true);
    },
    onError: (error) => {
      console.error('프로필 업데이트 실패:', error);
    },
  });

  const handleSubmitClick = (e: React.MouseEvent<HTMLButtonElement>) => {
    e.preventDefault();
    mutation.mutate(formData);
  };

  const [isModalVisible, setIsModalVisible] = useState(false);
  const handleCloseModal = () => {
    setIsModalVisible(false);
  };

  return (
    <ContainerLayout>
      {isModalVisible && <Modal children='성공적으로 업데이트되었습니다.' btn1='확인' onClose={handleCloseModal} />}
      <TopNavigation />
      <UserProfileFormWrap>
        <ProfileImg profileType={ProfileType.User} onValueChange={(value) => handleSignupForm('img', value)} />
        <Input inputType={InputType.NickName} onInputValue={(value) => handleSignupForm('nickname', value)} value={formData.nickname} />
        <RoleDropdown onValueChange={(value) => handleSignupForm('userRole', value)} value={formData.userRole} />
        <Button onClick={handleSubmitClick} disabled={isFormIncomplete}>
          저장하기
        </Button>
      </UserProfileFormWrap>
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

export default MyProfilePage;
