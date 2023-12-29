import React, { useState } from 'react';
import Image from 'next/image';
import styled from 'styled-components';
import TaskAltRoundedIcon from '@mui/icons-material/TaskAltRounded';
import PetsRoundedIcon from '@mui/icons-material/PetsRounded';
import WaterDropRoundedIcon from '@mui/icons-material/WaterDropRounded';
import ContentCutRoundedIcon from '@mui/icons-material/ContentCutRounded';
import ShowerRoundedIcon from '@mui/icons-material/ShowerRounded';
import CakeRoundedIcon from '@mui/icons-material/CakeRounded';
import LocalHospitalRoundedIcon from '@mui/icons-material/LocalHospitalRounded';

interface IScheduleTypeDropdownProps {
    onValueChange: (value: string) => void;
  }
  const options = [
    {label: '산책', icon: <PetsRoundedIcon style={{ color: '#C5A0F6'}}/>}, 
    {label: '밥 주기', icon:<Image src='/svgs/food.svg' alt='밥' width={20} height={20} />}, 
    {label: '물 교체', icon:<WaterDropRoundedIcon style={{ color: '#9ad8ed'}}/>}, 
    {label: '간식', icon: <Image src='/svgs/pet_supplies.svg' alt='간식' width={20} height={20} />}, 
    {label: '미용', icon:<ContentCutRoundedIcon style={{ color: '#FFC267'}}/>},
    {label: '배변', icon:<Image src='/svgs/poop.svg' alt='배변' width={20} height={20} />},
    {label: '양치', icon: <Image src='/svgs/dentistry.svg' alt='양치' width={20} height={20} />},
    {label: '목욕', icon:<ShowerRoundedIcon style={{ color: '#8295FD'}}/>},
    {label: '병원', icon:<LocalHospitalRoundedIcon style={{ color: '#81CF34'}}/>},
    {label: '생일', icon:<CakeRoundedIcon style={{ color: '#E68CB2'}}/>},
    ];

const ScheduleTypeDropdown = ({onValueChange }: IScheduleTypeDropdownProps) => {
    const [isOpen, setIsOpen] = useState(false);
    const [selectedValue, setSelectedValue] = useState(options[0]); 

        const handleSelect = (label: string) => {
            const selectedOption = options.find(option => option.label === label);
            if (selectedOption) {
                setSelectedValue(selectedOption); 
                onValueChange(label);
            }
            setIsOpen(false); 
        };
        
      
  return (
    <ScheduleTypeSelectWrap>
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
           {options.map(option => (
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

export default ScheduleTypeDropdown;
