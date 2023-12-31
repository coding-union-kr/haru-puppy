import React, { useState } from 'react';
import Image from 'next/image';
import 'react-datepicker/dist/react-datepicker.css';
import DatePicker from 'react-datepicker';
import styled from 'styled-components';
import CalendarMonthRoundedIcon from '@mui/icons-material/CalendarMonthRounded';


interface IDateDropdownProps {
    onValueChange: (date: Date) => void;
  }

const DateDropdown = ({onValueChange }: IDateDropdownProps) => {
    const [isOpen, setIsOpen] = useState(false);
    const [selectedDate, setSelectedDate] = useState(new Date()); 

    const handleDateSelect = (date: Date) => {
        setSelectedDate(date); 
        onValueChange(date);
        setIsOpen(false); 
      };
      
  return (
    <DateSelectWrap>
      <label htmlFor='schedule-date'>
        <span><CalendarMonthRoundedIcon/></span>
        날짜
      </label>
      <div onClick={() => setIsOpen(!isOpen)}>
      {selectedDate.toLocaleDateString()}
        <Image 
          src='/svgs/cover-box.svg'
          alt='드롭다운 열기' 
          width={20} 
          height={20} />
      </div>
      <DateDropdownWrap>

      {isOpen && <StyledDatePicker selected={selectedDate} onChange={handleDateSelect} inline className='react-datepicker-custom-b'/>}
    </DateDropdownWrap>
    </DateSelectWrap>
    )
};

const DateSelectWrap = styled.div`
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
    line-height: 40px;
    margin-top: 10px;
    text-align: center;
    font-size: 14px;
    border: 1px solid #E6E6E6;
    border-radius: 10px;
    & > img {
        position: absolute;
        top: 10px;
        right: 10px;
    }
}
`

const DateDropdownWrap = styled.span`
    position: absolute;
    top: 100%;
    z-index: 1000;
    width: 320px;
    margin-top: 10px;
    text-align: center;
`
const StyledDatePicker = styled(DatePicker)`
  &.react-datepicker-custom-b {
    .react-datepicker {
      border: 1px solid #dcdde1; 
      border-radius: 20px;
    }

    .react-datepicker__header {
      background-color: #FCFCFC;
      color: white; 
      border-bottom: none;
      border-radius: 20px;
    }

    .react-datepicker__day--selected {
      background-color: #FA9E52; 
      color: white; 
    }

    .react-datepicker__day--selected,
    .react-datepicker__day--selected:hover {
      border-radius: 50%; 
      border: none; 
    }
  }
`;


export default DateDropdown;
