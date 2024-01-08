"use client"

import React, { useState, useEffect } from 'react'
import axios from 'axios';
import { useMutation } from 'react-query';
import { useRouter } from 'next/navigation';
import DateDropdown, { DateDropdownLabel } from '../../../schedule/components/DateDropdown';
import { LOCAL_STORAGE_KEYS } from '@/app/constants/auth';
import dayjs from 'dayjs';
import ProfileImg, {ProfileType} from '@/app/components/profile/ProfileImg';
import GenderSelect from '../../../profile/dog/components/GenderSelect';
import Input, { InputType } from '@/app/components/input/Input';
import styled from 'styled-components';
import Button from '@/app/components/button/Button';
import ContainerLayout from '@/app/components/layout/layout';
import TopNavigation from '@/app/components/navigation/TopNavigation';
import { IUser } from '../../../../interfaces/User';
import { IDog } from '../../../../interfaces/Dog';


const DogRegisterPage = () => {
    interface IRequestData {
        userRequest: IUser;
        dogRequest: IDog;
        homeName: string;
    }
    const router = useRouter();
    const [requestData, setRequestData] = useState<IRequestData>({
        userRequest: {},
        dogRequest: {
            name: '',
            gender: '',
            birthday: dayjs().format('YYYY-MM-DD'),
            weight: 0,
            imgUrl: 'src://',
        }, 
        homeName: ''
    });

    useEffect(() => {
        const storedUserRequest = sessionStorage.getItem('userRequestData');
        if (storedUserRequest) {
            setRequestData(prev => ({
                ...prev,
                userRequest: JSON.parse(storedUserRequest)
            }));
        } else {
            router.push('/auth/register/user');
        }
    }, [router]);


    //필수입력 상태값   
    const [requiredField, setRequiredField] = useState<{ name: boolean; gender: boolean; weight: boolean }>({
        name: false,
        gender: false,
        weight: false,
    });

    const handleSelectChange = (name: string, value: any) => {

        if (name === 'birthday' && value instanceof Date) {
            value = dayjs(value).format('YYYY-MM-DD');
        }

        const newData = {
            ...requestData,
            [name]: value
        };

        setRequestData(prev => {
            const updatedHomeName = name === 'name' ? `${value}네 집` : prev.homeName;
            
            return {
                ...prev,
                dogRequest: {
                    ...prev.dogRequest,
                    [name]: value
                },
                homeName: updatedHomeName
            };
        });

        console.log('업데이트 된 requestData:', newData);

        setRequiredField({
            ...requiredField,
            [name]: value !== '' && value !== null,
        });

    };

    //필수 입력란 체크 boolean
    const areAllFieldsFilled = requiredField.name && requiredField.gender && requiredField.weight;

    const postApi = (data: IRequestData) => {
        console.log('API 요청 몇번 가냐', data);
        return axios.post('http://localhost:8080/api/users/register', data);
    };
    const mutation = useMutation(postApi, {
        onSuccess: (res) => {
            console.log('응답 데이터',res);
            const accessToken = res.data.data.token.accessToken;
            const homeId = res.data.data.homeResponse.homeId;

            localStorage.setItem(LOCAL_STORAGE_KEYS.ACCESS_TOKEN, accessToken);
            localStorage.setItem(LOCAL_STORAGE_KEYS.HOME_ID, homeId);
            console.log(accessToken);
            if (accessToken) {
                localStorage.setItem('token', accessToken);
                router.push('/');
            } else {
                console.error('accessToken이 응답에 포함되지 않았습니다.');
            }
            router.push('/')
        },
        onError: (error) => console.error('가입 실패:', error)
    });

    //signUp 요청 함수
    const handleSignUpClick = () => {
        if (requestData.userRequest) {
            mutation.mutate(requestData);
        }
    };

    return ( 
        <ContainerLayout>
            <TopNavigation/>
            <ComponentsWrapper>
            <ProfileImg profileType={ProfileType.Dog} onValueChange={(value)=> handleSelectChange('imgUrl', value)}/>
                <Input
                    inputType={InputType.DogName}
                    onInputValue={(value) => handleSelectChange('name', value)}
                />
                <GenderSelect onValueChange={(value) => handleSelectChange('gender', value)} />
                <DateDropdown onValueChange={(value) => handleSelectChange('birthday', value)} label={DateDropdownLabel.Birthday} isRequired={false} size={DateDropdownLabel.Birthday} />
                <Input
                    inputType={InputType.Weight}
                    onInputValue={(value) => handleSelectChange('weight', value)}
                />
                <ButtonWrapper>
                    <Button onClick={handleSignUpClick} disabled={!areAllFieldsFilled}>
                        가입 완료하기
                    </Button>
                </ButtonWrapper>
            </ComponentsWrapper>
        </ContainerLayout>
    )
}

const ButtonWrapper = styled.div`
    margin-top: 40px;
`

const ComponentsWrapper = styled.div`
    display: grid;
    grid-template-rows: repeat(4, minmax(20px, 1fr));
    grid-gap: 30px; 
    justify-content: center;
    align-items: center;
    width: 390px;
    & > div:first-of-type {
        width: 100%;
        display: flex;
        justify-content: center;
        align-items: center;
    }
`
export default DogRegisterPage;
