'use client'

import { getYear, getMonth, getDate, subDays, addDays } from 'date-fns';
import { useEffect, useState } from 'react';
import DatePicker from 'react-datepicker';
import 'react-datepicker/dist/react-datepicker.css';
import styled from 'styled-components';
import ChevronRightIcon from '@mui/icons-material/ChevronRight';
import ChevronLeftIcon from '@mui/icons-material/ChevronLeft';
import TodoCard from '../card/TodoCard';

export interface ScheduleItem {
  scheduleId: number;
  scheduleType: string;
  mates: {
    userId: number, user_img: string
  }[];
  scheduleDate?: string;
  reservedDate?: string;
  time: string;
  repeatId?: string | null;
  active: boolean;
}

export interface ScheduleResponse {
  schedule: ScheduleItem[];
}



const currentYear = getYear(new Date());
const YEARS = [currentYear - 1, currentYear, currentYear + 1, currentYear + 2];
const MONTHS = [
  '1월',
  '2월',
  '3월',
  '4월',
  '5월',
  '6월',
  '7월',
  '8월',
  '9월',
  '10월',
  '11월',
  '12월',
];


const Calendar = () => {
  const [date, setDate] = useState(new Date());
  const [scheduleData, setScheduleData] = useState<ScheduleResponse | null>(null);
  const [selectedDateTasks, setSelectedDateTasks] = useState<ScheduleItem[]>([]);
  const [markedDates, setMarkedDates] = useState<Date[]>([new Date('2023-12-01'), new Date('2023-12-05'), new Date('2023-12-10')]);

  useEffect(() => {
    const fetchScheduleData = async () => {
      const year = getYear(date);
      const month = getMonth(date) + 1;

      try {
        const response = await fetch(`/api/schedule/month/${year}/${month}`);
        const data: ScheduleResponse = await response.json();
        setScheduleData(data);
        const dateObjects = data.schedule.map((item) => new Date(item.scheduleDate || item.reservedDate || ''));
        setMarkedDates(dateObjects);
      } catch (error) {
        console.error('Month 스케줊 페칭 에러', error);
      }
    };

    fetchScheduleData();
  }, [date]);

  const renderDayContents = (dayOfMonth: number, date?: Date | null): React.ReactNode => {
    if (!date) return null;

    const formattedDate = date.toISOString().split('T')[0];
    const isDateMarked = markedDates.some((markedDate) => formattedDate === markedDate.toISOString().split('T')[0]);

    return (
      <div>
        {dayOfMonth}
        {isDateMarked && <Dot />}
      </div>
    );
  };

  const handleDateClick = async (clickedDate: Date) => {
    try {
      const formattedDate = clickedDate.toISOString().split('T')[0];
      const activeScheduleItem = scheduleData?.schedule.find((item) => item.scheduleDate === formattedDate && item.active);
      const inactiveScheduleItem = scheduleData?.schedule.find((item) => item.reservedDate === formattedDate && !item.active);

      if (activeScheduleItem) {
        const response = await fetch(`/api/schedule/${activeScheduleItem.scheduleId}`);
        const data = await response.json();
        setSelectedDateTasks(data);
      } else if (inactiveScheduleItem) {
        setSelectedDateTasks([]);
      }
    } catch (error) {
      console.error('특정 날짜 스케줄 목록 조회 에러', error);
    }
  };

  return (
    <Wrapper>
      <DatePicker
        renderDayContents={renderDayContents}
        selected={date}
        onChange={(newDate: Date) => {
          setDate(newDate);
          handleDateClick(newDate);
        }}
        inline
        dayClassName={(d) => (d.getDate() === date!.getDate() ? 'selectedDay' : 'unselectedDay')}
        calendarClassName={'calenderWrapper'}
        closeOnScroll={true}
        renderCustomHeader={({
          date,
          changeYear,
          decreaseMonth,
          increaseMonth,
          prevMonthButtonDisabled,
          nextMonthButtonDisabled,
        }) => (
          <CustomHeaderContainer>
            <Button type='button' onClick={decreaseMonth} disabled={prevMonthButtonDisabled}>
              <ChevronLeftIcon />
            </Button>
            <div>
              <select value={getYear(date)} onChange={({ target: { value } }) => changeYear(+value)}>
                {YEARS.map((option) => (
                  <option key={option} value={option}>
                    {option}
                  </option>
                ))}
              </select>
              <span>{MONTHS[getMonth(date)]}</span>
            </div>

            <Button type='button' onClick={increaseMonth} disabled={nextMonthButtonDisabled}>
              <ChevronRightIcon />
            </Button>
          </CustomHeaderContainer>
        )}
      />
      <TodoCard todoList={selectedDateTasks} />
    </Wrapper>
  );
};


const Wrapper = styled.div` 
display: flex;
flex-direction:column;
  justify-content: center;
  align-items: center;
  
  
`;

const Dot = styled.div`
  position: absolute;
  bottom: 5px;
  left: 50%;
  transform: translateX(-50%);
  width: 6px;
  height: 6px;
  background-color: #e15f41;
  border-radius: 50%;
`;

const CustomHeaderContainer = styled.div`
  display: flex;
  justify-content: space-between;
  align-items: center;
  background-color: #FCFCFC;
  height: 100%;
  margin-top: 8px;
  padding: 5px;

  & > div {
    display: flex;
    align-items: center;
    justify-content: center;

    & > * {
      margin-right: auto;
      margin-left: auto; 
    }

    & > span {
      color: #5b5b5b;
      font-size: 20px;
      font-weight: 400;
    }

    & > select {
      background-color: #FCFCFC;
      color: #5b5b5b;
      border: none;
      margin-right: 12px;
      font-size: 20px;
      font-weight: 400;
      padding-right: 5px;
      cursor: pointer;
    }
  }
`;

const Button = styled.button`
    width: 34px;
    height: 34px;
    padding: 5px;
    border-radius: 50%;
    svg {
      color: ${({ theme }) => theme.colors.main};
    }
    &:hover {
      background-color: rgba(#FFFFFF, 0.08);
    }
    &:disabled {
      cursor: default;
      background-color:  ${({ theme }) => theme.colors.main};
    }
`


export default Calendar;