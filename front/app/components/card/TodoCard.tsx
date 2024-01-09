import React, { useState } from 'react';
import styled from 'styled-components';
import Image from 'next/image';
import { ScheduleItem } from '@/app/_types/schedule/Schedule';

interface ITodoCardProps {
  todoList: ScheduleItem[];
}

const TodoCard = ({ todoList }: ITodoCardProps) => {
  const [todos, setTodos] = useState(todoList);

  const handleCheckboxChange = (scheduleId: number) => {
    const updatedTodos = todos.map((todo) =>
      todo.scheduleId === scheduleId ? { ...todo, active: !todo.active } : todo
    );
    setTodos(updatedTodos);
  };

  const activeTodos = todos.filter((todo) => todo.active);
  const inactiveTodos = todos.filter((todo) => !todo.active);

  return (
    <Wrapper>
      {activeTodos.length > 0 && (
        <TodoListWrapper>
          <CardTitle>완료</CardTitle>
          {activeTodos.map((todo, index) => (
            <TodoItem key={index}>
              <Checkbox
                type='checkbox'
                checked
                onChange={() => handleCheckboxChange(todo.scheduleId)}
              />
              <TodoText active={!todo.active}>{todo.scheduleType}</TodoText>
              <MateImgWrapper>
                {todo.mates.map((mate, mateIndex) => (
                  <MateImg alt='메이트 이미지' key={mateIndex} index={mateIndex} src={mate.user_img} />
                ))}
              </MateImgWrapper>
            </TodoItem>
          ))}
        </TodoListWrapper>
      )}

      {inactiveTodos.length > 0 && (
        <TodoListWrapper>
          <CardTitle>오늘</CardTitle>
          {inactiveTodos.map((todo, index) => (
            <TodoItem key={index}>
              <Checkbox
                type='checkbox'
                checked={!todo.active}
                onChange={() => handleCheckboxChange(todo.scheduleId)}
              />
              <TodoText active={!todo.active}>{todo.scheduleType}</TodoText>
              <MateImgWrapper>
                {todo.mates.map((mate, mateIndex) => (
                  <MateImg alt='메이트 이미지' key={mateIndex} index={mateIndex} src={mate.user_img} />
                ))}
              </MateImgWrapper>
            </TodoItem>
          ))}
        </TodoListWrapper>
      )}

      <RegisteredMate></RegisteredMate>
      {(activeTodos.length === 0 && inactiveTodos.length === 0) && (
        <CenteredMessage>일정을 추가해주세요!</CenteredMessage>
      )}
    </Wrapper>
  );
};

const CenteredMessage = styled.p`
 display: flex;
  font-size: 16px;
  color: ${({ theme }) => theme.colors.black80};
  margin-top: 50%;

`;


const Wrapper = styled.div`
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;

`;

const TodoListWrapper = styled.div`
  width: 349px;
  background-color: #FFFFFF;
  margin: 20px;
  border-radius: 15px;
  padding-top: 3px;
  padding-left: 10px;
  display: flex;
  flex-direction: column;
  justify-content: flex-start;
  box-shadow: 0px 3px 3px rgba(0, 0, 0, 0.5);
  min-height: 70px; 
`;


const TodoItem = styled.div`
  display: flex;
  width: 340px;
  height: 50px;
  border-radius: 15px;
  align-items: center;
  margin-bottom: 10px;
  transition: background-color 0.3s ease; 
  &:hover {
    background-color: #f0f0f0; 
  }
`;

const CardTitle = styled.div`
  color: ${({ theme }) => theme.colors.black80};
  font-size: 16px;
  margin-top: 15px;
  margin-left: 5px;
  margin-bottom: 10px;
`;

const Checkbox = styled.input.attrs<{ checked?: boolean }>(({ checked }) => ({
  checked: checked || false,
}))`
  margin: 10px;
  border: none;
  border-radius: 50%;
  width: 25px;
  height: 25px;
  appearance: none;
  width: 1.5rem;
  height: 1.5rem;
  border: 1.5px solid gainsboro;
  border-radius: 50%;
  &:checked {
    border-color: transparent;
    background-image: url("data:image/svg+xml,%3csvg viewBox='0 0 16 16' fill='white' xmlns='http://www.w3.org/2000/svg'%3e%3cpath d='M5.707 7.293a1 1 0 0 0-1.414 1.414l2 2a1 1 0 0 0 1.414 0l4-4a1 1 0 0 0-1.414-1.414L7 8.586 5.707 7.293z'/%3e%3c/svg%3e");
    background-size: 100% 100%;
    background-position: 50%;
    background-repeat: no-repeat;
    background-color: #06ACF4;
  }`


const TodoText = styled.p<{ active: boolean }>`
  font-weight: ${({ theme }) => theme.typo.regular};
  flex: 1;
  color: ${({ theme }) => theme.colors.black90};
  text-decoration: ${({ active }) => (active ? 'line-through' : 'none')};
`;

const MateImgWrapper = styled.div`
  width: 50px;
  height: 30px;
  display: flex;
  right: 0px;
  position: relative;
`;

const MateImg = styled(Image) <{ index: number }>`
  width: 25px;
  height: 25px;
  border-radius: 50%;
  background-color: ${({ theme }) => theme.colors.black70};
  position: absolute;
  left: ${(props) => props.index * 18}px;
`;

const RegisteredMate = styled.div`
  font-size: 12px;
  color: ${({ theme }) => theme.colors.black60};
`;


export default TodoCard;