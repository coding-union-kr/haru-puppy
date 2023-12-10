import React, { useState, useEffect } from "react";
import styled from "styled-components";

interface IInput {
    type: string;
    label: string;
    inputId: string;
    placeholder: string;
    errMsg?: string;
    minLength?: number;
    maxLength?: number;
    onInputValue: (value: string | number) => void;
}

const Input = ({type, label, inputId, placeholder, errMsg, minLength, maxLength, onInputValue} : IInput) => {
    const [inputValue, setInputValue] = useState<string | number>("");
    const [showErr, setShowErr] = useState<boolean>(false);
    
    const isValueEmpty = (value: string) => {
        if (type === 'text' && typeof value === 'string' && value.length < 3) 
          return true; 
        if (type === 'number' && value === '')
          return true; 
    
        return false; 
      }

    const onInputChange = (event: React.ChangeEvent<HTMLInputElement>) => {
        let value = event.target.value;
      
        const isEmpty = isValueEmpty(value);
        setShowErr(isEmpty);

        if (type === "number") {
          const numValue = parseFloat(value);
          if (!isNaN(numValue) || numValue < 0) {   
            const positiveNum = Math.abs(numValue); //양수로 변환
            const roundedValue = positiveNum.toFixed(1); //소수점 이하 첫째자리까지로 변환
            setInputValue(parseFloat(roundedValue));
          } 
        } else {
          setInputValue(value);
        }
           
        console.log(inputValue);
        event.target.placeholder = isEmpty ? placeholder : "";

        onInputValue(inputValue);
      };

      const onBlurHandler = (event: React.FocusEvent<HTMLInputElement>) => {
        const isEmpty = isValueEmpty(event.target.value);
      
        if (!isEmpty) {
          event.target.value = inputValue.toString();
        }
      };

      useEffect(() => {
        onInputValue(inputValue);
      }, [inputValue, onInputValue]);

   return (
    <InputWrap>
        <label htmlFor={inputId}>{label}<span>*</span></label>
        <input type={type} id={inputId} name="nickname" placeholder={placeholder} minLength={minLength} maxLength={maxLength} onChange={onInputChange} onBlur={onBlurHandler}/>
        {showErr && <span>{errMsg}</span>}
    </InputWrap>
   )
};

const InputWrap = styled.div<{showErr?:boolean}>`
 width: 340px;
 height: 74px; 

 & label {
    display: block;
    font-size: 14;
    font-weight: 400;
    margin-bottom: 18px;
   & span {
    margin-left: 8px;
    color: ${({theme})=> theme.colors.alert};
   }
 }

 & input {
    position: relative;
    width: 340px;
    padding: 15px 0;
    border: 1px solid ${({showErr, theme}) => showErr ? theme.colors.alert:theme.colors.black50};  
    border-radius: 10px;             
    color:#000000;
    text-align: center;
    font-weight: 400;
    font-size: 14px;
    &:focus { 
    border-color: ${({theme}) => theme.colors.black80};
   }
 } 

 & > span {
    display: block;
    margin-top: 8px;
    margin-left: 2px;
    font-weight: 400;
    font-size: 14px;
    color: ${({theme})=> theme.colors.alert};
 }
`

  export default Input;
  