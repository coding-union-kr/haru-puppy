"use client"

import ContainerLayout from '@/app/components/layout/layout';
import Calendar, { ScheduleItem } from '@/app/components/schedule/FullCalendar'
import Image from 'next/image';
import React, { useState } from 'react'
import styled from 'styled-components';

const page = () => {
    const onAddBtnClick = () => {
        console.log('일정 추가 팝업')
    }
    return (
        <>
            <Wrapper>
                <Calendar />
                <AddBtnWrapper onClick={onAddBtnClick}>
                    <Image src='/svgs/add_circle.svg' alt='add_circle' width={50} height={50} />
                </AddBtnWrapper>
            </Wrapper>

        </>
    )

}


const Wrapper = styled.div`
    position: relative;
      background-color: ${({ theme }) => theme.colors.background};
      height: 748px;
      width: 390px;
      justify-content: center;
      top: 0;
      margin: 0 auto;

`

const AddBtnWrapper = styled.div`
    position: absolute;
    width: 50px;
    height: 50px;
    background-color: white;
    border-radius: 50%;
    bottom: -25px;
    justify-content: center;
    align-items: center;
    left: 166px;
`

export default page
