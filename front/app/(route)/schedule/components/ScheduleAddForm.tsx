
import { useState } from 'react';
import Image from 'next/image';
import dayjs from 'dayjs';
import Button from "@/app/components/button/Button";
import styled from "styled-components";
import MateSelect from './MateSelect';
import { dummyMatesData } from '@/app/page';
import MemoTextArea from '@/app/components/input/MemoTextArea';
import ScheduleTypeSelect from './ScheduleTypeSelect';
import DateSelect, { DateSelectLabel } from '@/app/components/profile/DateSelect';
import TimeSelect from './TimeSelect';
import RepeatSelect from './RepeatSelect';
import NotiSelect from './NotiSelect';

export interface IScheduleAddFormProps {
    isOpen: boolean;
    onToggle: () => void;
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

const ScheduleAddForm = ({ isOpen, onToggle }: IScheduleAddFormProps) => {

    const [formData, setFormData] = useState<IFormData>({
        type: '산책',
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
        <>
        {isOpen && <Overlay onClick={onToggle} />} 
        <ScheduleAddWrap isOpen={isOpen}>
            <FormWrap>
                <ScheduleTypeSelect onValueChange={(value) => handleSelectChange('type', value)} />
                <MateSelect onValueChange={(value) => handleSelectChange('mates', value)} mates={dummyMatesData} />
                <DateSelect onValueChange={(value) => handleSelectChange('date', value)} label={DateSelectLabel.ScheduleDay} isRequired={true} />
                <TimeSelect onValueChange={(value) => handleSelectChange('time', value)} />
                <RepeatSelect onValueChange={(value) => handleSelectChange('repeat', value)} />
                <NotiSelect onValueChange={(value) => handleSelectChange('noti', value)} />
                <MemoTextArea onValueChange={(value) => handleSelectChange('memo', value)} />
                <ButtonGroupWrap>
                    <Button onClick={handleSave} width="135px" height="32px">저장</Button>
                    <Button onClick={handleDelete} width="135px" height="32px">삭제</Button>
                </ButtonGroupWrap>
            </FormWrap>
            <CloseButton onClick={onToggle}>
                <Image
                    src='/svgs/close_grey.svg'
                    alt='닫기'
                    width={24}
                    height={24} />
            </CloseButton>
        </ScheduleAddWrap>
        </>
    )
};

const Overlay = styled.div`
    position: fixed;
    top: 0;
    left: 0;
    right: 0;
    bottom: 0;
    background-color: rgba(0, 0, 0, 0.5); 
    z-index: 999; 
`;

const ScheduleAddWrap = styled.main<{ isOpen: boolean }>`
    padding: 37px 0 61px;
    width: 390px;
    height: 620px;
    border-top-left-radius:10px;
    border-top-right-radius: 10px;
    border: 1px solid #e6e6e6;
    position: fixed;
    bottom: 0;
    left: 50%;
    right: 50%;
    background: white;
    border-top-left-radius: 10px;
    border-top-right-radius: 10px;
    transition: transform 0.3s ease-out;
    transform: ${({ isOpen }) => (isOpen ? 'translateX(-50%)' : 'translateX(-50%) translateY(100%)')};
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
export default ScheduleAddForm;
