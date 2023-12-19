import React, { useState } from 'react';
import styled from 'styled-components';
import PeopleOutlineRoundedIcon from '@mui/icons-material/PeopleOutlineRounded';
import MateProfile from '@/app/components/profile/MateProfile';
import { Imates } from './ScheduleAdd';


interface IMateSelectProps {
  onValueChange: (value: Imates[]) => void;
  mates: Imates[] | null;
}

const MateSelect = ({ onValueChange, mates }: IMateSelectProps) => {
  const [selectedMates, setSelectedMates] = useState<string[]>([]);

  const handleMateClick = (userId: string) => {
    const isSelected = selectedMates.includes(userId);
    const newSelectedMates = isSelected
      ? selectedMates.filter((mateId) => mateId !== userId)
      : [...selectedMates, userId];

    setSelectedMates(newSelectedMates);
    const selectedMateObjects = mates?.filter((mate) => newSelectedMates.includes(mate.user_id)) || [];
    onValueChange(selectedMateObjects);
  };

  return (
    <MateSelectWrap>
      <label htmlFor='schedule-type'>
        <span><PeopleOutlineRoundedIcon /></span>
        담당 선택
      </label>
      <MateProfileWrapper>
        {mates?.map((mate) => (
          <MateProfile
            key={mate.user_id}
            mate={mate}
            isClicked={selectedMates.includes(mate.user_id)}
            onClick={handleMateClick}
          />
        ))}
      </MateProfileWrapper>
    </MateSelectWrap>
  )
};


const MateProfileWrapper = styled.div`
  display: flex;
  justify-content: center;
`
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
