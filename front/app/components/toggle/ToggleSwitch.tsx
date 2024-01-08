import styled from 'styled-components';

interface IToggleSwitchProps {
    isToggled: boolean;
    onToggle: (toggled: boolean) => void;
}


const ToggleSwitch = ({isToggled, onToggle} : IToggleSwitchProps) => {

  return (
    <ToggleContainer onClick={() => onToggle(!isToggled)} isToggled={isToggled}>
        <ToggleSlider isToggled={isToggled} />
    </ToggleContainer>
  );
};

const ToggleContainer = styled.div<{ isToggled: boolean }>`
  width: 50px; 
  height: 25px; 
  background-color: ${({ isToggled, theme }) => (isToggled ? theme.colors.main: '#ccc')}; 
  border-radius: 25px;
  position: relative;
  cursor: pointer;
`;

const ToggleSlider = styled.div<{ isToggled: boolean }>`
  background-color: #fff;
  height: 20px; 
  width: 20px; 
  position: absolute;
  top: 2px; 
  left: ${({ isToggled }) => (isToggled ? '26px' : '2px')}; 
  border-radius: 50%;
  transition: left 0.2s;
`;

export default ToggleSwitch;