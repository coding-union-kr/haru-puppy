"use client"

import React, { useState } from 'react'
import GenderSelect from './components/GenderSelect'
import DateDropdown, { DateDropdownLabel } from '../../schedule/components/DateDropdown';
import dayjs from 'dayjs';
import Input, { InputType } from '@/app/components/input/Input';
import styled from 'styled-components';
import Button from '@/app/components/button/Button';


interface IDogProfile {
    dog_id: string;
    name: string;
    gender: string;
    birthday: string;
    weight: number;
    img: string;
}


const page = () => {
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
        <PageWrapper>
            <ComponentsWrapper>
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
        </PageWrapper>
    )
}


const PageWrapper = styled.div`
    display: flex;
    justify-content: center;
    align-items: center;
    height: 100vh;   
`;

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

`

export default page
