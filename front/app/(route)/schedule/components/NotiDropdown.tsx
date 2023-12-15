
import {useState} from 'react';
import Select from './Select';
import NotificationsNoneRoundedIcon from '@mui/icons-material/NotificationsNoneRounded';
import styled from "styled-components";

interface INotiSelectProps {
    onValueChange: (value: string) => void;
}

const NotiDropdown = ({ onValueChange }: INotiSelectProps) => {
    const [selectedValue, setSelectedValue] = useState('');
    const options = ['없음', '정각', '5분 전', '30분 전', '1시간 전'];

    const handleSelect = (value: string) => {
        setSelectedValue(value);
        onValueChange(value);
    };
    
    return (
        <Select icon={<NotificationsNoneRoundedIcon />} label="알림" selectId="schedule-noti" selectedValue={selectedValue}
        onValueChange={handleSelect}
        >
             <NotiDropdownWrap>
             {options.map(option => (
                    <li key={option} onClick={() => handleSelect(option)}>
                            {option}
                    </li>
                ))}
            </NotiDropdownWrap>
        </Select>
    )
}

const NotiDropdownWrap = styled.ul`
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
`
export default NotiDropdown;