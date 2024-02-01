'use client';

import { useRecoilValue } from 'recoil';
import { userState } from '@/app/_states/userState';
import ContentCopyIcon from '@mui/icons-material/ContentCopy';
import styled from 'styled-components';
import ContainerLayout from '@/app/components/layout/layout';
import TopNavigation from '@/app/components/navigation/TopNavigation';

const InvitePage = () => {
  const userData = useRecoilValue(userState);
  const homeId = userData.homeId;
  console.log(homeId);

  async function shareLink() {
    const inviteUrl = `http://localhost:3000/auth/login?homeId=${homeId}`;

    if (navigator.share) {
      try {
        await navigator.share({
          title: '초대 링크',
          url: inviteUrl,
        });
        console.log('초대 성공');
      } catch (error) {
        console.error('공유 실패', error);
      }
    } else {
      console.log('이 브라우저는 공유 기능을 지원하지 않습니다');
    }
  }

  return (
    <ContainerLayout>
      <TopNavigation />
      <InvitePageWrap>
        <strong>강아지를 같이 돌볼 메이트를 초대해주세요!</strong>
        <InviteButtonWrap onClick={shareLink}>
          <ContentCopyIcon width={20} height={20} />
          초대 링크 복사하기
        </InviteButtonWrap>
      </InvitePageWrap>
    </ContainerLayout>
  );
};

const InvitePageWrap = styled.div`
  display: flex;
  flex-direction: column;
  align-items: center;
  & > strong {
    font-weight: ${({ theme }) => theme.typo.semibold};
  }
`;
const InviteButtonWrap = styled.button`
  display: flex;
  justify-content: flex-start;
  align-items: center;
  margin-top: 300px;
  width: 370px;
  height: 56px;
  border-radius: 10px;
  font-size: 18px;
  font-weight: ${({ theme }) => theme.typo.regular};
  background-color: ${({ theme }) => theme.colors.light};
  color: #000000;
  & > :nth-child(1) {
    margin: 0 28px 0 53px;
  }
`;

export default InvitePage;
