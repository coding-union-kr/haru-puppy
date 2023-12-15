import React, { useState } from 'react';
import styled from 'styled-components';
import PeopleOutlineRoundedIcon from '@mui/icons-material/PeopleOutlineRounded';
import MateProfile from '@/app/components/profile/MateProfile';

interface IMateSelectProps {
    onValueChange: (value: Array<{ userId: string }>) => void;
  }

const MateSelect = ({onValueChange }: IMateSelectProps) => {
    const [selectedValue, setSelectedValue] = useState<Array<{ userId: string }>>([]);

    const handleSelect = (value: { userId: string }) => {
        setSelectedValue(prevSelectedValue => [...prevSelectedValue, value]);
        onValueChange([...selectedValue, value]);
    };
        
  return (
    <MateSelectWrap>
      <label htmlFor='schedule-type'>
        <span><PeopleOutlineRoundedIcon/></span>
        담당 선택
      </label>
    <MateProfile/>
    </MateSelectWrap>
    )
};

const MateSelectWrap = styled.div`
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
`


export default MateSelect;
