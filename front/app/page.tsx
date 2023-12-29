"use client"
import { Pretendard } from "@/public/fonts/fonts";
import styled from "styled-components";

const Font1 = styled.h1`
  font-size: 10;
  color: ${({ theme }) => theme.colors.main};
  font-weight: ${({ theme }) => theme.typo.regular}
`

const Font2 = styled.p`
  font-size: 20;
  font-weight: 500;
  font-weight: ${({ theme }) => theme.typo.semibold}

`

const Font3 = styled.div`
  font-size: 10;
  font-weight: 600;
`

export default function Home() {
  return (
    <main className={Pretendard.className} >
      <div>
        <Font1>
          곰곰
        </Font1>
        <Font2>
          곰곰
        </Font2>
        <Font3>
          곰곰
        </Font3>
      </div>
    </main>
  )
}

