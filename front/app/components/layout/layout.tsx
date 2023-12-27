import styled from 'styled-components';

const ContainerLayout = ({
  children, 
}: {
  children: React.ReactNode;
}) => {

  return (
    <Container>
      {children}
    </Container>
  );
};

     
const Container = styled.div`
    width: 100%;
    max-width: 24.375rem;
    height: 100vh;
    margin: 0 auto;
    display: flex;
    justify-content: center;
    align-items: center;

    @media (max-width: 768px) {
        width: 100%;
    }
    @media (max-width: 844px) {
        width: 100%;
        margin: 0 auto;
    }
    @media (min-width: 1025px) {
        width: 36rem; 
    }
`;


export default ContainerLayout;


