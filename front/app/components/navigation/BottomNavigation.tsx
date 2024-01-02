import BottomNavigationIcon from "./BottomNavigationIcon";
import HomeRoundedIcon from '@mui/icons-material/HomeRounded';
import EventAvailableRoundedIcon from '@mui/icons-material/EventAvailableRounded';
import SettingsRoundedIcon from '@mui/icons-material/SettingsRounded';
import styled from "styled-components";

const BottomNavigation = () => {
    return (
        <BottomNavigationWrap>
            <ul>
                <BottomNavigationIcon icon={<HomeRoundedIcon />} title="홈" route='/' />
                <BottomNavigationIcon icon={<EventAvailableRoundedIcon />} title="일정" route='/schedule' />
                <BottomNavigationIcon icon={<SettingsRoundedIcon />} title="설정" route='/setting' />
            </ul>
        </BottomNavigationWrap>
    )
};

const BottomNavigationWrap = styled.nav`
    position: fixed;
    bottom: 0;
    left: 50%;
    transform: translateX(-50%);
    background-color: #FFFFFF;
    width: 100%;
    max-width: 390px; 
    border-top: 0.5px solid ${({ theme }) => theme.colors.black60};

    & > ul {
        display: flex;
        justify-content: space-between;
        padding: 0 25px;
        margin: 0; 
    }
`;

export default BottomNavigation;
