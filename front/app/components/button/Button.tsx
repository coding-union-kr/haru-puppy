import styled from 'styled-components';

interface IButtonProps {
  onClick: (e: React.MouseEvent<HTMLButtonElement>) => void;
  disabled?: boolean;
  width?: string;
  height?: string;
  children: string;
}

const Button: React.FC<IButtonProps> = ({ onClick, disabled = false, width, height, children }) => {
  return (
    <ButtonWrap onClick={onClick} width={width} height={height} disabled={disabled}>
      {children}
    </ButtonWrap>
  );
};

const ButtonWrap = styled.button<{ disabled?: boolean; width?: string; height?: string }>`
  width: ${({ width }) => width || '370px'};
  height: ${({ height }) => height || '56px'};
  border-radius: ${({ height }) => (height == '32px' ? '6px' : '10px')};
  background-color: ${({ disabled, theme }) => (disabled ? theme.colors.black60 : theme.colors.main)};
  color: #ffffff;
  font-weight: ${({ theme }) => theme.typo.semibold};
  font-size: ${({ height }) => (height == '32px' ? '16px' : '18px')};
  cursor: ${({ disabled }) => (disabled ? 'not-allowed' : 'pointer')};
`;
export default Button;
