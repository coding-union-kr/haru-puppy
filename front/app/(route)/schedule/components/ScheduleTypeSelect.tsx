import React, { useRef, useState, useEffect} from 'react';
;
import { scheduleTypeOptions } from '../../../constants/scheduleTypeOptions';
import Image from 'next/image';
import styled from 'styled-components';
import TaskAltRoundedIcon from '@mui/icons-material/TaskAltRounded';


interface IScheduleTypeSelectProps {
    onValueChange: (value: string) => void;
  }

const ScheduleTypeSelect = ({ onValueChange }: IScheduleTypeSelectProps) => {
    const [isOpen, setIsOpen] = useState(false);
    const [selectedValue, setSelectedValue] = useState(scheduleTypeOptions[0]); 
    const dropdownRef = useRef<HTMLDivElement>(null);

    const handleSelect = (label: string) => {
        const selectedOption = scheduleTypeOptions.find(option => option.label === label);
        if (selectedOption) {
            setSelectedValue(selectedOption); 
            onValueChange(label);
        }
        setIsOpen(false); 
    };

    const handleClickOutside = (event: MouseEvent) => {
        if (event.target instanceof Node) {
            if (dropdownRef.current && !dropdownRef.current.contains(event.target)) {
            setIsOpen(false);
            }
        }
        };

    useEffect(() => {
        document.addEventListener('mousedown', handleClickOutside);
        return () => {
            document.removeEventListener('mousedown', handleClickOutside);
        };
    }, []);
        
      
  return (
    <ScheduleTypeSelectWrap ref={dropdownRef}>
      <label htmlFor='schedule-type'>
        <span><TaskAltRoundedIcon/></span>
        활동 유형
      </label>
      <div onClick={() => setIsOpen(!isOpen)}>
      {selectedValue.icon} {selectedValue.label}
        <DropImg
          src='/svgs/cover-box.svg'
          alt='드롭다운 열기' 
          width={20} 
          height={20} />
      </div>
      <span>
      {isOpen && <ScheduleTypeDropdownWrap>
           <ul>
           {scheduleTypeOptions.map(option => (
                    <li key={option.label} onClick={() => handleSelect(option.label)}>
                          {option.icon} {option.label}
                    </li>
                ))}
           </ul>
            </ScheduleTypeDropdownWrap>}
    </span>
    </ScheduleTypeSelectWrap>
    )
};

const ScheduleTypeSelectWrap = styled.div`
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

& > div {
    position: relative;
    height: 40px;
    line-height: 40px;
    margin-top: 10px;
    text-align: center;
    font-size: 14px;
    border: 1px solid #E6E6E6;
    border-radius: 10px;
    & > img, .MuiSvgIcon-root {
        font-size: 20px;
        vertical-align: middle;
        margin-right: 5px;
    }

}
`
const DropImg = styled(Image)`
     position: absolute;
     top: 10px;
     right: 10px;
`

const ScheduleTypeDropdownWrap = styled.div`
    position: absolute;
    top: 100%; 
    left: 50%;
    transform: translateX(-50%);
    z-index: 1000;
    margin-top: 10px;
    background-color: #ffffff;
    border: 1px solid ${({ theme }) => theme.colors.black50};
    border-radius: 10px;

& > ul {
    width: 150px;
    display: flex;
    flex-direction: column;
    align-items: center;

     & > li {
        .MuiSvgIcon-root, img {
          font-size: 20px; 
          margin-right: 5px;
          vertical-align: middle;
       }
        position: relative;
        cursor: pointer;
        text-align: center;
        width: 100%;
        font-size: 14px;
        color: ${({theme}) => theme.colors.black90};
        padding: 15px 0;
        &:hover {
            background-color:  #F7FBFF;
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
} 
`

export default ScheduleTypeSelect;
