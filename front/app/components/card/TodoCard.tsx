import React, { useState } from 'react';
import styled from 'styled-components';

interface ITodoCardProps {
  todoList: {
    id: number;
    task: string;
    mates: {
      name: string;
      profileImg: string;
    }[];
    createdAt: string;
    completed: boolean;
  }[];
}


const TodoCard = ({ todoList }: ITodoCardProps) => {
  const [todos, setTodos] = useState(todoList);

  const handleCheckboxChange = (id: number) => {
    const updatedTodos = todos.map((todo) =>
      todo.id === id ? { ...todo, completed: !todo.completed } : todo
    );
    setTodos(updatedTodos);
  };

  const uncompletedTodos = todos.filter((todo) => !todo.completed);
  const completedTodos = todos.filter((todo) => todo.completed);

  return (
    <Wrapper>
      <TodoListWrapper>
        <CardTitle>오늘</CardTitle>
        {uncompletedTodos.map((todo, index) => (
          <TodoItem key={index}>
            <Checkbox type='checkbox' onChange={() => handleCheckboxChange(todo.id)} />
            <TodoText completed={todo.completed}>{todo.task}</TodoText>
            <MateImgWrapper>
              {todo.mates.map((mate, mateIndex) => (
                <MateImg key={mateIndex} index={mateIndex} />
              ))}
            </MateImgWrapper>
          </TodoItem>
        ))}
      </TodoListWrapper>

      <TodoListWrapper>
        <CardTitle>완료</CardTitle>
        {completedTodos.map((todo, index) => (
          <TodoItem key={index}>
            <Checkbox type='checkbox' checked onChange={() => handleCheckboxChange(todo.id)} />
            <TodoText completed={todo.completed}>{todo.task}</TodoText>
            <MateImgWrapper>
              {todo.mates.map((mate, mateIndex) => (
                <MateImg key={mateIndex} index={mateIndex} />
              ))}
            </MateImgWrapper>
          </TodoItem>
        ))}
      </TodoListWrapper>
      <RegisteredMate></RegisteredMate>
    </Wrapper>
  );
};



const Wrapper = styled.div`
  display: flex;
  flex-direction: column;

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
  align-items: center;
  margin-bottom: 10px;
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
  margin-right: 10px;
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
  }
  `


const TodoText = styled.p<{ completed: boolean }>`
  font-weight: ${({ theme }) => theme.typo.regular};
  flex: 1;
  color: ${({ theme }) => theme.colors.black90};
  text-decoration: ${({ completed }) => (completed ? 'line-through' : 'none')};
`;

const MateImgWrapper = styled.div`
  width: 50px;
  height: 30px;
  display: flex;
  right: 0px;
  position: relative;
`;

const MateImg = styled.div<{ index: number }>`
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