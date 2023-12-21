import React, { useState } from "react";
import styled from "styled-components";

export enum InputType {
  Email = 'Email',
  Password = 'Password',
  PasswordCheck = 'PasswordCheck',
  NickName = 'NickName',
  DogName = 'DogName',
  Weight = 'Weight'
}
interface IInput {
  inputType: InputType;
  parentErrMsg?: string;
  onInputValue: (value: string | number) => void;
}
interface InputConfig {
  type: string;
  inputId: string;
  label: string;
  placeholder: string;
  errMsg?: string;
  minLength?: number;
  maxLength?: number;
}

const inputConfig: Record<InputType, InputConfig> = {
  Email: {
    type: 'email',
    inputId: 'email',
    label: '이메일',
    placeholder: '이메일을 입력하세요.',
    errMsg: '유효한 이메일 주소를 입력하세요.',
    maxLength: 50
  },
  Password: {
    type: 'password',
    inputId: 'password',
    label: '비밀번호',
    placeholder: '비밀번호를 입력하세요.',
    errMsg: '비밀번호는 최소 8자 이상이어야 합니다.',
    minLength: 8,
    maxLength: 20
  },
  PasswordCheck: {
    type: 'password',
    inputId: 'password-check',
    label: '비밀번호 확인',
    placeholder: '비밀번호를 한번 더 입력하세요.',
    minLength: 8,
    maxLength: 20
  },
  NickName: {
    type: 'text',
    inputId: 'nickname',
    label: '닉네임',
    placeholder: '닉네임을 입력하세요.',
    errMsg: '닉네임은 최소 2자 이상이어야 합니다.',
    minLength: 2,
    maxLength: 15
  },
  DogName: {
    type: 'text',
    inputId: 'dog-name',
    label: '강아지 이름',
    placeholder: '강아지 이름을 입력하세요.',
    errMsg: '강아지 이름은 최소 2자 이상이어야 합니다.',
    minLength: 2,
    maxLength: 15
  },
  Weight: {
    type: 'number',
    inputId: 'weight',
    label: '체중',
    placeholder: '강아지 체중을 입력하세요.',
    errMsg: '올바른 체중을 입력해주세요.'
  }
};

const Input = ({ inputType, parentErrMsg, onInputValue }: IInput) => {
  const [inputValue, setInputValue] = useState<string | number>("");
  const [showErr, setShowErr] = useState<boolean>(false);

  const { type, inputId, label, placeholder, errMsg, minLength, maxLength } = inputConfig[inputType];
  let finalErrMsg = inputType === InputType.PasswordCheck ? parentErrMsg : errMsg;

  const isValueEmpty = (value: string) => {
    if (type === 'text' && typeof value === 'string' && value.length < 2)
      return true;
    if (type === 'number' && value === '')
      return true;
    if (type === 'password' && value.length < 8)
      return true;

    return false;
  }
  const isEmailValid = (email: string) => {
    const regex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
    return regex.test(email);
  };

  const onInputChange = (event: React.ChangeEvent<HTMLInputElement>) => {
    let value = event.target.value;

    const isEmpty = isValueEmpty(value);
    event.target.placeholder = isEmpty ? placeholder : "";

    if (inputType === InputType.Weight) {
      const numValue = parseFloat(value);
      if (!isNaN(numValue) || numValue < 0) {
        const positiveNum = Math.abs(numValue); //양수로 변환
        const roundedValue = positiveNum.toFixed(1); //소수점 이하 첫째자리까지로 변환
        setInputValue(parseFloat(roundedValue));
      }
    } else {
      setInputValue(value);
    }
  };

  const onBlurHandler = (event: React.FocusEvent<HTMLInputElement>) => {
    let value = event.target.value;

    const isEmpty = isValueEmpty(value);
    setShowErr(isEmpty);

    if (inputType === InputType.Weight && !isEmpty) {
      value = inputValue.toString();
    }

    if (inputType === InputType.Email && !isEmailValid(value)) {
      setShowErr(true);
      return;
    }
    onInputValue(inputValue);
  };

  return (
    <InputWrap>
      <label htmlFor={inputId}>{label}<span>*</span></label>
      <input type={type} id={inputId} name={inputId} placeholder={placeholder} minLength={minLength} maxLength={maxLength} onChange={onInputChange} onBlur={onBlurHandler} />
      {showErr && <span>{finalErrMsg}</span>}
    </InputWrap>
  )
};

const InputWrap = styled.div<{ showErr?: boolean }>`
 width: 340px;
 height: 74px; 
 display: flex;
 flex-direction: column;
 margin: 0 auto;


 & label {
    font-size: 14;
    font-weight: 400;
    margin-bottom: 14px;
   & span {
    margin-left: 8px;
    color: ${({ theme }) => theme.colors.alert};
   }
 }

 & input {
    position: relative;
    width: 340px;
    padding: 15px 0;
    border: 1px solid ${({ showErr, theme }) => showErr ? theme.colors.alert : theme.colors.black50};  
    border-radius: 10px;             
    color:#000000;
    text-align: center;
    font-weight: 400;
    font-size: 14px;
    &:focus { 
    border-color: ${({ theme }) => theme.colors.black80};
   }
 } 

 & > span {
    margin-top: 8px;
    margin-left: 2px;
    font-weight: 400;
    font-size: 14px;
    color: ${({ theme }) => theme.colors.alert};
 }
`

export default Input;
