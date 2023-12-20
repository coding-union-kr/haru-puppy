"use client"

import {useState} from 'react';
import Button from "@/app/components/button/Button";
import Input, {InputType} from "@/app/components/input/Input";
import styled from "styled-components";
import ContainerLayout from '@/app/components/layout/layout';

const SignupPage = () => {
    interface FormData {
       email: string;
       password: string;
       passwordCheck: string;
    }
    const [passwordCheckErrMsg, setPasswordCheckErrMsg] = useState("");
    const [formData, setFormData] = useState<FormData>({
        email: '',
        password: '',
        passwordCheck: '',
    });
   const handleSignupForm = (name: string, value: any) => {
    const newFormData = {
        ...formData,
        [name]: value
    };
    if ((name === "passwordCheck" && value !== newFormData.password) ||
    (name === "password" && newFormData.passwordCheck && value !== newFormData.passwordCheck)) {
        setPasswordCheckErrMsg("비밀번호가 일치하지 않습니다.");
    } else {
        setPasswordCheckErrMsg("");
    }

    setFormData(newFormData);
    console.log('업데이트 된 formData:', newFormData);
   }
   return (
    <ContainerLayout>
    <SignupFormWrap>
    <Input inputType={InputType.Email} onInputValue={(value) => handleSignupForm('email', value)}/>
    <Input inputType={InputType.Password} onInputValue={(value) => handleSignupForm('password', value)}/>
    <Input inputType={InputType.PasswordCheck} onInputValue={(value) => handleSignupForm('passwordCheck', value)} parentErrMsg={passwordCheckErrMsg}/>
    <Button onClick={() => console.log('api요청 보내는 함수 만들기', formData)}>회원가입</Button>
    </SignupFormWrap>
    </ContainerLayout>
   )
};

const SignupFormWrap = styled.form`
    display: flex;
    flex-direction: column;
    align-items: center;
    margin-top: 65px;
    & > div{
        margin-bottom: 45px;
    }

    & > button {
        margin-top: 150px;
    }
`

export default SignupPage;
  