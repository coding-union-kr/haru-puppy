import React, { useState } from 'react';
import styled from 'styled-components';

interface DogGenderSelectorProps {
    onValueChange: (value: string) => void;
}

const GenderSelect = ({ onValueChange }: DogGenderSelectorProps) => {

    const genderOptions = ['MALE', 'FEMALE'];

    const [selectedGender, setSelectedGender] = useState<string>('');

    const handleGenderClick = (gender: string) => {
        console.log('gender', gender)
        setSelectedGender(gender);
        onValueChange(gender);
    };

    return (
        <Wrapper>
            <Title><p>성별</p><span>*</span></Title>
            <GenderWrapper>
                {genderOptions.map((gender) => (
                    <GenderButton key={gender} selected={selectedGender === gender} onClick={() => handleGenderClick(gender)}>
                        {gender === 'FEMALE' ? '여아' : '남아'}
                    </GenderButton>
                ))}
            </GenderWrapper>
        </Wrapper>
    );
};

const Wrapper = styled.div`
    width: 340px;
    height: 74px;
    margin: 0 auto;

`;


const Title = styled.div`
    display: flex;  
    margin-bottom: 5px;
    & span {
    margin-left: 8px;
    color: ${({ theme }) => theme.colors.alert};
   }
     & > p {
        font-size: 14px;
    }
`

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
  font-size: 14px;
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