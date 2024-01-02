import React, { useState } from 'react';
import styled from 'styled-components';
import Image from 'next/image';

interface IDateDropdownProps {
    onValueChange: (value: string) => void;
  }

const RoleDropdown = ({onValueChange }: IDateDropdownProps) => {
    const [isOpen, setIsOpen] = useState(false);
    const [selectedValue, setSelectedValue] = useState('엄마'); 
    const options = ['아빠', '엄마', '언니/누나', '오빠/형'];

    const handleSelect = (value: string) => {
        setSelectedValue(value); 
        onValueChange(value);
        setIsOpen(false); 
      };
      
  return (
    <RoleSelectWrap>
      <label htmlFor='role'>
        나는 우리 강아지의...
      </label>
      <div onClick={() => setIsOpen(!isOpen)}>
        {selectedValue}
        <Image 
          src='/svgs/cover-box.svg'
          alt='드롭다운 열기' 
          width={20} 
          height={20} />
      </div>
      {isOpen &&
            <RoleDropdownWrap>
                {options.map(option => (
                    <li key={option} onClick={() => handleSelect(option)}>
                        {option}
                    </li>
                ))}
            </RoleDropdownWrap>}
    </RoleSelectWrap>
    )
};

const RoleSelectWrap = styled.div`
position: relative;
width: 340px;
display: flex;
flex-direction: column;
cursor: pointer;

& > label {
    font-size: 16px;
    font-weight: ${({ theme }) => theme.typo.regular};
}

& > div {
    position: relative;
    line-height: 48px;
    margin-top: 10px;
    text-align: center;
    font-size: 14px;
    border: 1px solid #E6E6E6;
    border-radius: 10px;
    & > img {
        position: absolute;
        top: 14px;
        right: 10px;
    }
}
`
const RoleDropdownWrap = styled.ul`
    width: 150px;
    position: absolute;
    display: flex;
    flex-direction: column;
    align-items: center;
    margin-top: 10px;
    top: 100%;  
    left: 50%; 
    transform: translateX(-50%); 
    z-index: 1000;
    background-color: #ffffff;
    border: 1px solid ${({ theme }) => theme.colors.black50};
    border-radius: 10px;

     & > li {
        position: relative;
        cursor: pointer;
        text-align: center;
        width: 100%;
        font-size: 14px;
        color: ${({theme}) => theme.colors.black90};
        padding: 15px 0;
        &:hover {
            background-color: #F7FBFF;
        }
     }

     & > li:not(:last-child)::after {
        content: ''; 
        position: absolute;
        bottom: 0; 
        left: 0; 
        width: 100%;
        border-bottom: 1px solid ${({theme}) => theme.colors.black60}; 
    }

     & > li:first-child{
        &:hover {
            border-top-right-radius: 10px;
            border-top-left-radius:10px;
        }
     }

     & > li:last-child {
        &:hover {
            border-bottom-right-radius: 10px;
            border-bottom-left-radius:10px;
        }
     }    
`

export default RoleDropdown;
