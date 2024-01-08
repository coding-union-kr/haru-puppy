import React from 'react'
import styled from 'styled-components'
import PetsIcon from '@mui/icons-material/Pets';
import dayjs from 'dayjs';
import Image from 'next/image';


interface IDummyData {
    today_poo_cnt: number,
    last_week_walk_cnt: number,
    last_wash_date: string,
    last_hospital_date: string,
}

interface IReportCard {
    dummyReports: IDummyData

}

const ReportCard = ({ dummyReports }: IReportCard) => {
    const reportsArray = [
        { title: '오늘의 배변활동', count: dummyReports.today_poo_cnt, unit: '회', icon: <Image src={'/svgs/poop.svg'} alt="배변활동 아이콘" width={30} height={30} /> },
        { title: '지난주 산책', count: dummyReports.last_week_walk_cnt, unit: '회', icon: <Image src={'/svgs/paw.svg'} alt="산책 아이콘" width={25} height={25} /> },
        { title: '마지막 목욕', count: dayjs(dummyReports.last_wash_date).format('MM.DD'), icon: <Image src={'/svgs/dog_bath.svg'} alt="마지막 목욕 아이콘" width={30} height={30} /> },
        { title: '마지막 검진', count: dayjs(dummyReports.last_hospital_date).format('MM.DD'), icon: <Image src={'/svgs/dog_health_check.svg'} alt="마지막 검진 아이콘" width={30} height={30} /> },
    ];
    return (
        <>
            <ReportCardWrapper>
                <Title>멍순이 리포트</Title>

                {reportsArray.map((report, index) => (
                    <Wrapper key={index}>
                        <p>{report.title}</p>
                        <Info>
                            {report.icon}
                            <Count>
                                {report.count !== null && report.count !== 0 ? (
                                    <>
                                        {report.count}
                                        <p>{report.unit}</p>
                                    </>
                                ) : (
                                    '-'
                                )}
                            </Count>
                        </Info>
                    </Wrapper>
                ))}
            </ReportCardWrapper>
        </>
    )
}

const ReportCardWrapper = styled.div`
    width: 370px;
    display: grid;
    grid-template-columns: repeat(2, 1fr);
    gap: 20px;
    padding: 20px;
    `;

const Title = styled.span`
    font-size: 20px;
    grid-column: span 2; 
    text-align: start; 
    margin-bottom: 10px; 
`;

const Wrapper = styled.div`
    width: 156px;
    height: 84px;
    margin: 0 auto;
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