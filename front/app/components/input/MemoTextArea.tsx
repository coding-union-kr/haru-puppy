import React, { useState } from "react";
import styled from "styled-components";
import EditNoteRoundedIcon from '@mui/icons-material/EditNoteRounded';

interface IMemoInputProps {
    onValueChange: (value: string) => void;
}
const MemoTextArea = ({onValueChange} : IMemoInputProps) => {
    const [textAreaValue, setTextAreaValue] = useState<string>("");
    const [placeholder, setPlaceholder] = useState('메모를 입력해주세요.');

    const onTextAreaChange = (event: React.ChangeEvent<HTMLTextAreaElement>) => {
        const value = event.target.value;
        setTextAreaValue(value);
        setPlaceholder(value !== '' ? '' : '메모를 입력해주세요.');
    };

    const onBlurHanlder = (event: React.FocusEvent<HTMLTextAreaElement>) => {
        onValueChange(textAreaValue);
    }

    return (
        <InputWrap>
            <label htmlFor='schedule-memo'>
            <span><EditNoteRoundedIcon /></span>
            메모
            </label>
           <textarea id='schedule-memo' name='schedule-memo' placeholder={placeholder} minLength={2} maxLength={200} onChange={onTextAreaChange} onBlur={onBlurHanlder}/>
        </InputWrap>
      )
}

const InputWrap = styled.div`
position: relative; 
width: 300px;
display: flex;
flex-direction: column;
cursor: pointer;

& > label {
    font-size: 14px;
    font-weight: ${({ theme }) => theme.typo.regular};
    & > span {
        margin-right: 10px;
        vertical-align: middle;
    }
}

 & textarea {
    margin-top: 10px;
    position: relative;
    width: 298px;
    min-height: 50px;
    padding: 15px;
    border: 1px solid ${({ theme }) => theme.colors.black50};  
    border-radius: 10px;             
    color:#000000;
    font-weight: 400;
    font-size: 14px;
    resize: none;
    &:focus { 
    border-color: ${({ theme }) => theme.colors.black80};
   }
 } 
`

export default MemoTextArea;