import React, { useState } from 'react';
import styled from 'styled-components';

export interface DogGenderSelectorProps {
    onValueChange?: () => void;
}

const genderOptions = ['male', 'female'];

const GenderSelect = ({ onValueChange }: DogGenderSelectorProps) => {
    const [selectedGender, setSelectedGender] = useState<string>('');

    const handleGenderClick = (gender: string) => {
        console.log('gender', gender)
        setSelectedGender(gender);
        // onValueChange(gender);
    };

    return (
        <GenderWrapper>
            {genderOptions.map((gender) => (
                <GenderButton key={gender} selected={selectedGender === gender} onClick={() => handleGenderClick(gender)}>
                    {gender === 'female' ? '여아' : '남아'}
                </GenderButton>
            ))}
        </GenderWrapper>
    );
};

const GenderWrapper = styled.div`
    display: flex;
    justify-content: center;
    align-items: center;
`;

const GenderButton = styled.div<{ selected: boolean }>`
  width: 160px;
  height: 38px;
  margin: 5px;
  display: flex;
  justify-content: center;
  align-items: center;
  cursor: pointer;
  border: 1px solid ${({ theme }) => theme.colors.main};
  border-radius: 10px;
  background-color: ${({ selected, theme }) => (selected ? theme.colors.main : 'white')};
  color: ${({ selected, theme }) => (selected ? 'white' : theme.colors.black90)};
  transition: background-color 0.3s ease;
`;

export default GenderSelect;