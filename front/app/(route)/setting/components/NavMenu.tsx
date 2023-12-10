import ArrowForwardIosRoundedIcon from '@mui/icons-material/ArrowForwardIosRounded';
import styled from "styled-components";

interface INavMenu {
    title: string;
    onClick: () => void;
}

const NavMenu = ({title, onClick} : INavMenu) => {
   return (
    <NavMenuWrap onClick={onClick}>
        <div>
            <strong>{title}</strong>
            <StyledArrowIcon/>
        </div>
    </NavMenuWrap>
   )
};

const NavMenuWrap = styled.nav`
width: 324px;
height: 30px;
border-bottom: 1px solid ${({theme}) => theme.colors.black50};

& > div {
    margin-left: 2px;
    display: flex;
    flex-direction: row;
    justify-content: space-between;
    font-size: 16px;
    font-weight: ${({theme}) => theme.typo.regular};
    color: ${({theme}) => theme.colors.black90};
    cursor: pointer;
}
`

const StyledArrowIcon = styled(ArrowForwardIosRoundedIcon)`
  font-size: 16px; 
  color: ${({theme}) => theme.colors.black80};
`;

  export default NavMenu;
  