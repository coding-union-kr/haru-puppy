
import {useState} from 'react';
import Select from './Select';
import ReplayRoundedIcon from '@mui/icons-material/ReplayRounded';
import styled from "styled-components";

interface IRepeatDropdownProps {
    onValueChange: (value: string) => void;
}

const RepeatDropdown = ({ onValueChange }: IRepeatDropdownProps) => {
    const [selectedValue, setSelectedValue] = useState('');
    const options = ['없음', '매일', '매주', '매월', '매년'];

    const handleSelect = (value: string) => {
        setSelectedValue(value);
        onValueChange(value);
    };
    
    return (
        <Select icon={<ReplayRoundedIcon/>} label="반복" selectId="schedule-repeat" selectedValue={selectedValue}  
        onValueChange={handleSelect}
        >
           <RepeatDropdownWrap>
             {options.map(option => (
                    <li key={option} onClick={() => handleSelect(option)}>
                            {option}
                    </li>
                ))}
            </RepeatDropdownWrap>
        </Select>
    )
}

const RepeatDropdownWrap = styled.ul`
    width: 150px;
    display: flex;
    flex-direction: column;
    align-items: center;

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

export default RepeatDropdown;