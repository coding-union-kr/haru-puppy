import Image from 'next/image'
import React from 'react'
import styled from 'styled-components'

const Loading = () => {
  return (
    <Wrapper>
      <Image src='/svgs/loading.gif' alt='loading spinner' width={200} height={200} />
    </Wrapper>
  )
}


const Wrapper = styled.div`
  display: flex;
  height: 100vh;
  justify-content: center;
  align-items: center;
  margin: 0 auto;
`

export default Loading
