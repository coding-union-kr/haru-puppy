import React from 'react'
import styled from 'styled-components'
import PetsIcon from '@mui/icons-material/Pets';


interface IReportCard {
    title: string;
    count: string | number;
    unit: string | undefined;
    icon: React.ReactNode;
}

const ReportCard = ({ title, count, unit, icon }: IReportCard) => {
    return (
        <Wrapper>
            <p>{title}</p>
            <Info>
                {icon}
                <Count>
                    {count}<p>{unit}</p>
                </Count>
            </Info>
        </Wrapper>
    )
}

const Wrapper = styled.div`
    width: 156px;
    height: 84px;
    border: 2px solid ${({ theme }) => theme.colors.black60};
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
    margin-top: 10px;
    svg {
    width: 40px; 
    height: 40px;
    fill: purple;    
  }
`;


const Count = styled.div`
    font-size: 28px;
    color: ${({ theme }) => theme.colors.black90};
    margin-left: 14px;
    display: flex;
    align-items: flex-end;
    padding-bottom: 5px;
    p {
        display: inline-block;
        font-size: 14px;
        margin-left: 5px;
        padding-bottom: 5px;
     
    }

`;

export default ReportCard