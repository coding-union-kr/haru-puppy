import React, { useState } from 'react'
import MateProfile from '../profile/MateProfile'
import { Imates } from '@/app/(route)/schedule/components/ScheduleAdd';
import styled from 'styled-components';
import Image from 'next/image';


interface IMateHomeProps {
    mates: Imates[]
}

const MateList = ({ mates }: IMateHomeProps) => {
    const [isEdit, setIsedit] = useState(false);


    const onEditClick = () => {
        setIsedit(!isEdit)
    }
    console.log('isEdit', isEdit)

    return (
        <Wrapper>
            <UpperWrapper>
                <p>메이트</p>
                <div onClick={onEditClick}>
                    {isEdit ? <Image src={'/svgs/home_edit_close_btn.svg'} alt='mate-edit-btn' width={30} height={30} /> :
                        <Image src={'/svgs/home_edit_btn.svg'} alt='mate-edit-btn' width={30} height={30} />}
                </div>
            </UpperWrapper>

            <ProfileWrapper>
                {mates?.map((mate) => (
                    <MateProfile key={mate.user_id} mate={mate} isEditClick={isEdit} size='60' />
                ))}
                <PlusWrapper>
                    {mates.length < 4 && isEdit && <Image src={'/svgs/mate_plus.svg'} alt='mate-edit-btn' width={24} height={24} />}
                </PlusWrapper>
            </ProfileWrapper>
        </Wrapper>
    )
}


const Wrapper = styled.div`
    display: flex;
    flex-direction: column;
    justify-content: center;
    align-items: center;  
    width: 370px;  
    padding: 20px;
`

const UpperWrapper = styled.div`
    display: flex;
    justify-content: space-between;
    width: 90%; 

`

const ProfileWrapper = styled.div`
  display: flex;
  gap: 10px;
  overflow-x: auto;
  width: 100%; 
  justify-content: center; 
  align-items: center;
`;


const PlusWrapper = styled.div`
    margin: -30px auto 0;    
`


export default MateList
