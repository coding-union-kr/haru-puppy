import React, { useState } from 'react';
import { LocalizationProvider } from '@mui/x-date-pickers';
import { AdapterDayjs } from '@mui/x-date-pickers/AdapterDayjs';
import { DigitalClock } from '@mui/x-date-pickers/DigitalClock';
import Select from './Select';
import dayjs from 'dayjs';
import AccessTimeRoundedIcon from '@mui/icons-material/AccessTimeRounded';
import styled from 'styled-components';

interface ITimeSelectProps {
    onValueChange: (value: any) => void;
}

const TimeSelect = ({ onValueChange }: ITimeSelectProps) => {
    const [selectedValue, setSelectedValue] = useState('');  

    const handleSelect = (value: Date | null | string) => {
        let formattedTime = '없음';

        if (value) {
            const dayjsDate = dayjs(value);
            if (dayjsDate.isValid()) {
                formattedTime = dayjsDate.format('HH:mm');
            }
        }

        setSelectedValue(formattedTime);
        onValueChange(formattedTime);
    };

    return (
        <LocalizationProvider dateAdapter={AdapterDayjs}>
        <Select
            icon={<AccessTimeRoundedIcon />}
            label="시간" 
            selectId="schedule-time"
            selectedValue={selectedValue}
            onValueChange={handleSelect}  
        >
            <StyledClock
                onChange={handleSelect}
            />
        </Select>
        </LocalizationProvider>
    );
};

const StyledClock = styled(DigitalClock)`
  &::-webkit-scrollbar {
    width: 10px;
  }

  &::-webkit-scrollbar-track {
    background: ${({theme}) => theme.colors.black50};
  }

  &::-webkit-scrollbar-thumb {
    background-color: ${({theme}) => theme.colors.main};
    border-radius: 10px;
  }

  scrollbar-width: thin;
  scrollbar-color: ${({theme}) => theme.colors.main} ${({theme}) => theme.colors.black50};
`;


export default TimeSelect;