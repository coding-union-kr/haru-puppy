"use client"
import { Pretendard } from "@/public/fonts/fonts";
import styled from "styled-components";
import UserProfile from "./components/home/UserProfile";
import WalkRank from "./components/chart/WalkRank";
import TodoCard from "./components/card/TodoCard";
import ReportCard from "./components/card/ReportCard";
import MateList from "./components/home/MateList";


export const dummyMatesData = [
  {
    user_id: '2222',
    user_img: 'image_url',
    nickname: '파파',
    role: '아빠',
  },
  {
    user_id: '3333',
    user_img: 'image_url',
    nickname: '브라더',
    role: '형',
  },
  {
    user_id: '3333',
    user_img: 'image_url',
    nickname: '브라더',
    role: '형',
  }
]


export const dummyReports = {
  today_poo_cnt: 2,
  last_week_walk_cnt: 7,
  last_wash_date: '2023-12-13 00:00:00',
  last_hospital_date: '2023-12-13 00:00:00'
}


export const dummyRanking = [
  {
    user_id: 1,
    user_img: '',
    nickname: 'User1',
    rank: 4,
  },
  {
    user_id: 2,
    user_img: '',
    nickname: 'User2',
    rank: 2,
  },
  {
    user_id: 3,
    user_img: '',
    nickname: 'User3',
    rank: 3,
  },
];

export default function Home() {


  return (
    <main>
      <Wrapper>
        <UserProfile />
        <MateList mates={dummyMatesData} />
        <ReportCard dummyReports={dummyReports} />
        <WalkRank ranking={dummyRanking} />
      </Wrapper>
    </main>
  )

}


const Wrapper = styled.div`
  display: flex;
  padding: 100px 20px;
  flex-direction:column;
  justify-content:center;
  align-items:center;
  margin: 0 auto;
  gap: 50px;
`


