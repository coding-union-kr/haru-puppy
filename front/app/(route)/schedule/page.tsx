"use client"

import ContainerLayout from '@/app/components/layout/layout';
import Calendar, { ScheduleItem } from '@/app/components/schedule/calendar'
import React, { useState } from 'react'
import styled from 'styled-components';

const page = () => {
    return (

        <Wrapper>
            <Calendar />
        </Wrapper>
    )
}


const Wrapper = styled.div`
      background-color: ${({ theme }) => theme.colors.background};
      height: 748px;
      width: 390px;
      justify-content: center;
      top: 0;
      margin: 0 auto;

`

export default page
