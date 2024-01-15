"use client"

import React, { useState } from 'react'
import DateSelect, { DateSelectLabel } from '@/app/components/profile/DateSelect';
import dayjs from 'dayjs';
import ProfileImg, { ProfileType } from '@/app/components/profile/ProfileImg';
import Input, { InputType } from '@/app/components/input/Input';
import styled from 'styled-components';
import Button from '@/app/components/button/Button';
import ContainerLayout from '@/app/components/layout/layout';
import TopNavigation from '@/app/components/navigation/TopNavigation';
import GenderSelect from '@/app/components/profile/GenderSelect';


interface IDogProfile {
    dog_id: string;
    name: string;
    gender: string;
    birthday: string;
    weight: number;
    img: string;
}

const DogProfilePage = () => {
    const [formData, setFormData] = useState<IDogProfile>({
        dog_id: '',
        name: '',
        gender: '',
        birthday: '',
        weight: 0,
        img: '',
    });

    //필수입력 상태값   
    const [requiredField, setRequiredField] = useState<{ name: boolean; gender: boolean; weight: boolean }>({
        name: false,
        gender: false,
        weight: false,
    });

    const handleSelectChange = (name: string, value: any) => {
        let formattedValue = value;

        if (name === 'birthday' && value instanceof Date) {
            value = dayjs(value).format('YYYY-MM-DD');
        }

        const newFormData = {
            ...formData,
            [name]: value
        };

        setFormData(newFormData);
        console.log('업데이트 된 formData:', newFormData);

        setRequiredField({
            ...requiredField,
            [name]: value !== '' && value !== null,
        });

    };

    //필수 입력란 체크 boolean
    const areAllFieldsFilled = requiredField.name && requiredField.gender && requiredField.weight;


    //signUp 요청 함수
    const handleSignUpClick = () => {
        console.log('signUp 성공')
    };

    return (
        <ContainerLayout>
            <TopNavigation />
            <ComponentsWrapper>
                <ProfileImg profileType={ProfileType.Dog} onValueChange={(value) => handleSelectChange('img', value)} />
                <Input
                    inputType={InputType.DogName}
                    onInputValue={(value) => handleSelectChange('name', value)}
                />
                <GenderSelect onValueChange={(value) => handleSelectChange('gender', value)} />
                <DateSelect onValueChange={(value) => handleSelectChange('birthday', value)} label={DateSelectLabel.Birthday} isRequired={false} />
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
export default DogProfilePage;




