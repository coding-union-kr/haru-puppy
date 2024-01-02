'use client'

import { getYear, getMonth, getDate, subDays, addDays, format } from 'date-fns';
import { useEffect, useState } from 'react';
import DatePicker from 'react-datepicker';
import 'react-datepicker/dist/react-datepicker.css';
import styled from 'styled-components';
import ChevronRightIcon from '@mui/icons-material/ChevronRight';
import ChevronLeftIcon from '@mui/icons-material/ChevronLeft';
import TodoCard from '../card/TodoCard';
import { AnimatePresence, motion } from 'framer-motion';
import ExpandMoreIcon from '@mui/icons-material/ExpandMore';
import { ko } from 'date-fns/locale';


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

const KOREAN_DAY_NAMES = ['일', '월', '화', '수', '목', '금', '토'];


const Calendar = () => {
  const [date, setDate] = useState(new Date());
  const [scheduleData, setScheduleData] = useState<ScheduleResponse | null>(null);
  const [selectedDateTasks, setSelectedDateTasks] = useState<ScheduleItem[]>([]);
  const [markedDates, setMarkedDates] = useState<Date[]>([new Date('2023-12-01'), new Date('2023-12-05'), new Date('2023-12-10')]);
  const month = getMonth(date) + 1;
  const year = getYear(date);

  const [showDatePicker, setShowDatePicker] = useState(false);

  const startOfWeek = subDays(date, date.getDay());



  useEffect(() => {
    const fetchScheduleData = async () => {

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

      // weekcalendar와 fullcalendar모두 상태 동일하게 업데이트
      setDate(clickedDate);
    } catch (error) {
      console.error('특정 날짜 스케줄 목록 조회 에러', error);
    }
  };

  return (
    <>
      <Wrapper>
        {/* <AnimatePresence mode='wait'> */}
        {showDatePicker ? (
          // <motion.div
          //   key="datepicker"
          //   initial={{ height: 0 }}
          //   animate={{ height: 'auto' }}
          //   exit={{ height: 0 }}
          //   transition={{ duration: 0.3 }}
          // >

          <DatePicker
            renderDayContents={renderDayContents}
            locale={ko}
            selected={date}
            onChange={(newDate: Date) => {
              setDate(newDate);
              handleDateClick(newDate);
            }}
            inline
            dayClassName={(d) => (d.getDate() === date!.getDate() ? 'selectedDay' : 'unselectedDay')}
            calendarClassName={'calenderWrapper'}
            closeOnScroll={true}
            renderCustomHeader={({ date, changeYear, decreaseMonth, increaseMonth, prevMonthButtonDisabled, nextMonthButtonDisabled }) => (
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
          // </motion.div>

        ) : (
          <>
            {/* <motion.div
              key="weekcalendar"
              initial={{ height: 0 }}
              animate={{ height: 'auto' }}
              exit={{ height: 0 }}
              transition={{ duration: 0.3 }}
            > */}

            <WeekCalendar>
              <Month><span>{month}월</span></Month>
              <DayWrapper>
                {Array.from({ length: 7 }).map((_, index) => {
                  const day = addDays(startOfWeek, index);
                  const dayOfWeek = KOREAN_DAY_NAMES[index];
                  return (
                    <>
                      <div
                        key={index}
                        onClick={() => handleDateClick(day)}
                        className={`weekDay ${format(day, 'd') === format(date, 'd') ? 'selectedDay' : ''}`}
                      >

                        <div className='dayOfWeek'>{dayOfWeek}</div>
                        {format(day, 'd')}

                      </div>
                    </>
                  );
                })}
              </DayWrapper>
            </WeekCalendar>
            {/* </motion.div> */}


          </>
        )}
        {/* </AnimatePresence> */}


        <ExpandMoreIcon onClick={() => setShowDatePicker(!showDatePicker)} />
      </Wrapper >

      <TodoCard todoList={selectedDateTasks} />
    </>
  );
};

const Wrapper = styled.div` 
display: flex;
flex-direction:column;
  justify-content: center;
  align-items: center;
  margin-top:100px;
  background-color: #FFFFFF;


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
  background-color: #FFFFFF;
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
      background-color: #FFFFFF;
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


const DayWrapper = styled.div`
  display: flex;
`

const WeekCalendar = styled.div`
  display: flex;
  flex-direction: column;
  width: 340px;
  height: 155px;
  border: 1px solid #DFDFDF;
  border-radius: 20px;
  padding: 0px 10px;
  div {
    align-items: center;
  justify-content: center;
    width: 100%;
    text-align: center;
    cursor: pointer;
    position: relative;
    padding-top: 35px;
    padding-bottom: 5px;
    border-radius: 12px;
    height: 20px;
    
    /* justify-content: center; */
    &.selectedDay {
      background-color: ${({ theme }) => theme.colors.main}; 
      color: white;
    }
    &.unselectedDay:hover {
      background-color: #f0f0f0;
      /* height: 25px; */
    }

    .dayOfWeek {
      position: absolute;
      bottom: 45%;
      left: 50%;
      transform: translateX(-50%);
      color: ${({ theme }) => theme.colors.black80};
      font-size: 15px;
      /* height: 20px; */
      /* margin: 5px 0px; */
      /* padding-top: 15px; */
    }
  }
`;

const Month = styled.div`
  height: 10px;
  top: -10px;
  span {
    font-size: 20px;
    color: ${({ theme }) => theme.colors.black90}
  }
`

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