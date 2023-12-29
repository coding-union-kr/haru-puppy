import React from 'react'
import styled from 'styled-components'
import PetsIcon from '@mui/icons-material/Pets';

const ReportCard = () => {
    return (
        <Wrapper>
            <p>지난주 산책</p>
            <Info>
                <PetsIcon />
                <Count>
                    9<p>회</p>
                </Count>
            </Info>
        </Wrapper>
    )
}

const Wrapper = styled.div`
    width: 156px;
    height: 84px;
    border: 2px solid ${({ theme }) => theme.colors.black70};
    border-radius: 12px;
    display: flex;
    flex-direction: column;
    justify-content: center;
    align-items: center;
    p {
  
        font-size: 14px;
        color: ${({ theme }) => theme.colors.black90}
    }
`;

const Info = styled.div`
    display: flex;
    margin-top: 5px;
    svg {
    width: 40px; 
    height: 40px;
    fill: purple;    
  }
`;


const Count = styled.div`
    font-size: 30px;
    color: ${({ theme }) => theme.colors.black90};
    margin-left: 20px;
    display: flex;
    align-items: flex-end;
    p {
        display: inline-block;
        font-size: 14px;
        margin-left: 5px;
        padding-bottom: 5px;
     
    }

`;

export default ReportCard