import ArrowForwardIosRoundedIcon from '@mui/icons-material/ArrowForwardIosRounded';
import styled from "styled-components";

interface INavMenu {
    title: string;
    onClick?: () => void;
    children?: JSX.Element;
}

const NavMenu = ({ title, onClick, children }: INavMenu) => {
    return (
        <NavMenuWrap onClick={onClick}>
            <div>
                <strong>{title}</strong>
                {children || <StyledArrowIcon />}
            </div>
        </NavMenuWrap>
    )
};

const NavMenuWrap = styled.nav`
width: 340px;
height: 30px;
border-bottom: 1px solid ${({ theme }) => theme.colors.black50};

& > div {
    margin-left: 2px;
    display: flex;
    flex-direction: row;
    justify-content: space-between;
    align-items: center;
    font-size: 16px;
    font-weight: ${({ theme }) => theme.typo.regular};
    color: ${({ theme }) => theme.colors.black90};
    cursor: pointer;
}
`

export const StyledArrowIcon = styled(ArrowForwardIosRoundedIcon)`
  font-size: 16px; 
  color: ${({ theme }) => theme.colors.black80};
`;

export default NavMenu;
