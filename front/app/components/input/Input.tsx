import React, { useState } from "react";
import styled from "styled-components";

export enum InputType {
  NickName = 'NickName',
  DogName = 'DogName',
  Weight = 'Weight'
}
interface IInputProps {
  inputType: InputType;
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

const Input = ({ inputType, onInputValue }: IInputProps) => {
  const [inputValue, setInputValue] = useState<string | number>("");
  const [showErr, setShowErr] = useState<boolean>(false);

  const { type, inputId, label, placeholder, errMsg, minLength, maxLength } = inputConfig[inputType];

  const isValueEmpty = (value: string) => {
    if (type === 'text' && typeof value === 'string' && value.length < 2)
      return true;
    if (type === 'number' && value === '')
      return true;
    if (type === 'password' && value.length < 8)
      return true;

    return false;
  }

  const onInputChange = (event: React.ChangeEvent<HTMLInputElement>) => {
    let value = event.target.value;

    const isEmpty = isValueEmpty(value);
    event.target.placeholder = isEmpty ? placeholder : "";

    setInputValue(value);
    setShowErr(isEmpty);
  };

  const onBlurHandler = (event: React.FocusEvent<HTMLInputElement>) => {
    let value = event.target.value;

    const isEmpty = isValueEmpty(value);
    setShowErr(isEmpty);

    if (inputType === InputType.Weight && value) {
        const numValue = parseFloat(value);
        if (!isNaN(numValue)) {
          const positiveNum = Math.abs(numValue);
          const roundedValue = positiveNum.toFixed(1);
          setInputValue(roundedValue); 
          onInputValue(parseFloat(roundedValue)); 
        }
    } else {
        onInputValue(value); 
    }
  };

  return (
    <InputWrap>
      <label htmlFor={inputId}>{label}<span>*</span></label>
      <input type={type} id={inputId} name={inputId} placeholder={placeholder} minLength={minLength} maxLength={maxLength}  value={inputValue} onChange={onInputChange} onBlur={onBlurHandler} />
      {showErr && <span>{errMsg}</span>}
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
