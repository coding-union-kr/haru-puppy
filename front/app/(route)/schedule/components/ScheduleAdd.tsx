
import { useState } from 'react';
import Image from 'next/image';
import dayjs from 'dayjs';
import NotiDropdown from './NotiDropdown';
import RepeatDropdown from './RepeatDropdown';
import Button from "@/app/components/button/Button";
import styled from "styled-components";
import TimeDropdown from './TimeDropdown';
import DateDropdown, { DateDropdownLabel } from './DateDropdown';
import ScheduleTypeDropdown from './ScheduleTypeDropdown';
import MateSelect from './MateSelect';
import { dummyMatesData } from '@/app/page';

import MemoTextArea from '@/app/components/input/MemoTextArea';

export interface IScheduleAddProps {
    isOpen: boolean;
    onClose?: () => void;
}

export interface Imates {
    user_id: string;
    user_img?: string;
    nickname?: string;
    role?: string;
}

export interface IFormData {
    type: string;
    mates: Imates[] | null;
    date: Date | null;
    time: Date | null;
    repeat: string;
    noti: string;
    memo: string;
}

const ScheduleAdd = ({ isOpen, onClose }: IScheduleAddProps) => {

    const [formData, setFormData] = useState<IFormData>({
        type: '',
        mates: null,
        date: null,
        time: null,
        repeat: '',
        noti: '',
        memo: '',
    });

    const handleSelectChange = (name: string, value: any) => {
        let formattedValue = value;

        if (name === 'date' && value instanceof Date) {
            value = dayjs(value).format('YYYY-MM-DD');
        }

        if (name === 'time' && value instanceof Date) {
            formattedValue = dayjs(value).format('HH:mm');
        }
        if (name === 'mates' && value instanceof Array) {
            formattedValue = value;
        }

        const newFormData = {
            ...formData,
            [name]: value
        };
        console.log('업데이트 된 formData:', newFormData);

        setFormData(newFormData);
    };

    const handleSave = () => {
        console.log('저장');
    }

    const handleDelete = () => {
        console.log('삭제');
    }

    return (
        <ScheduleAddWrap isOpen={isOpen}>
            <FormWrap>
                <ScheduleTypeDropdown onValueChange={(value) => handleSelectChange('type', value)} />
                <MateSelect onValueChange={(value) => handleSelectChange('mates', value)} mates={dummyMatesData} />
                <DateDropdown onValueChange={(value) => handleSelectChange('date', value)} dateDropdownType={DateDropdownLabel.ScheduleDay} isRequired={true} />
                <TimeDropdown onValueChange={(value) => handleSelectChange('time', value)} />
                <RepeatDropdown onValueChange={(value) => handleSelectChange('repeat', value)} />
                <NotiDropdown onValueChange={(value) => handleSelectChange('noti', value)} />
                <MemoTextArea onValueChange={(value) => handleSelectChange('memo', value)}/>
                <ButtonGroupWrap>
                    <Button onClick={handleSave} width="135px" height="32px">저장</Button>
                    <Button onClick={handleDelete} width="135px" height="32px">삭제</Button>
                </ButtonGroupWrap>
            </FormWrap>
            <CloseButton onClick={onClose}>
                <Image
                    src='/svgs/close_grey.svg'
                    alt='닫기'
                    width={24}
                    height={24} />
            </CloseButton>
        </ScheduleAddWrap>
    )
};

const ScheduleAddWrap = styled.main<{ isOpen: boolean }>`
     padding: 37px 0 61px;
     width: 390px;
     height: 712px;
     border-top-left-radius:10px;
     border-top-right-radius: 10px;
     border: 1px solid #e6e6e6;
     position: fixed;
    bottom: 0;
    left: 0;
    right: 0;
    background: white;
    border-top-left-radius: 10px;
    border-top-right-radius: 10px;
    transition: transform 0.3s ease-out;
    transform: ${({ isOpen }) => (isOpen ? 'translateY(0)' : 'translateY(100%)')};
    z-index: 1000;
`
const FormWrap = styled.form`
     overflow: hideen;
     display: flex;
     flex-direction: column;
     align-items: center;

     & > div {
        margin-bottom: 14px;
     }
`

const CloseButton = styled.button`
    position: absolute;
    top:5px;
    right: 5px;
`
const ButtonGroupWrap = styled.div`
    margin-top: 30px;
    & button:first-of-type {
        margin-right: 26px;
    }
`
export default ScheduleAdd;
