import React from 'react';
import styled from 'styled-components';
import { addDays, format, getMonth, getYear, subDays } from 'date-fns';

const KOREAN_DAY_NAMES = ['일', '월', '화', '수', '목', '금', '토'];

interface WeekCalendarProps {
    date: Date;
    handleDateClick: (day: Date) => void;
}

const WeekCalendar = ({ date, handleDateClick }: WeekCalendarProps) => {
    const startOfWeek = subDays(date, date.getDay());

    return (
        <Container>
            <Month>
                <span>{getMonth(date) + 1}월</span>
            </Month>
            <DayWrapper>
                {Array.from({ length: 7 }).map((_, index) => {
                    const day = addDays(startOfWeek, index);
                    const dayOfWeek = KOREAN_DAY_NAMES[index];

                    return (
                        <Day
                            key={index}
                            onClick={() => handleDateClick(day)}
                            className={`weekDay ${format(day, 'd') === format(date, 'd') ? 'selectedDay' : ''}`}
                        >
                            <div className='dayOfWeek'>{dayOfWeek}</div>
                            {format(day, 'd')}
                        </Day>
                    );
                })}
            </DayWrapper>
        </Container>
    );
};

const Container = styled.div`
  display: flex;
  flex-direction: column;
  width: 340px;
  height: 140px;
  border: 1px solid #DFDFDF;
  border-radius: 20px;
  padding: 0px 10px;
  justify-content: center;
  gap: 20px;
`;

const DayWrapper = styled.div`
  display: flex;
  margin-top: 5px;
`;

const Day = styled.div`
  width: 100%;
  text-align: center;
  cursor: pointer;
  position: relative;
  padding-top: 40px;
  border-radius: 12px;
  height: 30px;
  &.selectedDay {
    background-color: ${({ theme }) => theme.colors.main};
    color: white;
    .dayOfWeek {
      color: white; 
    }
  }

  &.unselectedDay:hover {
    background-color: #f0f0f0;
  }

  .dayOfWeek {
    position: absolute;
    bottom: 45%;
    left: 50%;
    transform: translateX(-50%);
    color: ${({ theme }) => theme.colors.black80};
    font-size: 15px;
    margin-bottom: 10px;
  }
`;

const Month = styled.div`
  height: 10px;
    margin: 0 auto;
  span {
    font-size: 20px;
    color: ${({ theme }) => theme.colors.black90};
  }
`;

export default WeekCalendar;