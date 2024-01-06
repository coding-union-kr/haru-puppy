import { LOCAL_STORAGE_KEYS } from "@/app/constants/auth";
import { useRouter } from "next/navigation";
import styled from "styled-components";

interface IModalProps {
    children: string;
    btn1: string;
    btn2?: string;
    onClose: () => void;
}

const Modal = ({ children, btn1, btn2, onClose }: IModalProps) => {
    const router = useRouter();

    const handleBtn1Click = () => {
        console.log("btn1 클릭");

        onClose();
    };

    const handleBtn2Click = () => {
        console.log("btn2 클릭");
        if (btn2 === '로그아웃') {
            localStorage.removeItem(LOCAL_STORAGE_KEYS.ACCESS_TOKEN);
            localStorage.removeItem(LOCAL_STORAGE_KEYS.REFRESH_TOKEN);
            router.push('/auth/login');
        }
        onClose();
    };

    return (
        <ModalOverlay onClick={onClose}>
            <div onClick={e => e.stopPropagation()}>
                <ModalWrap >
                    <strong>{children}</strong>
                    <ButtonGroup hasSecondButton={!!btn2}>
                        <button onClick={handleBtn1Click}>{btn1}</button>
                        {btn2 && <button onClick={handleBtn2Click}>{btn2}</button>}
                    </ButtonGroup>
                </ModalWrap>
            </div>
        </ModalOverlay>
    )
};

const ModalOverlay = styled.div`
    position: fixed;
    top: 0;
    left: 0;
    width: 100%;
    height: 100%;
    background-color: rgba(0, 0, 0, 0.5); 
    display: flex;
    justify-content: center;
    align-items: center;
`;

const ModalWrap = styled.div`
    position: relative;
    display: flex;
    flex-direction: column;
    align-items: center;
    width: 242px;
    height: 170px;
    border-radius: 10px;
    background-color: #ffffff;                
    color: #000000;
    font-weight: ${({ theme }) => theme.typo.regular};
    font-size: 16px;
    box-shadow: rgba(149, 157, 165, 0.2) 0px 8px 24px;

    & > strong {
     position: absolute;
     top: 60px;
    }
`

const ButtonGroup = styled.div<{ hasSecondButton: boolean }>`
    position: absolute;
     bottom: 25px;

    & > button {
        width: ${({ hasSecondButton }) => hasSecondButton ? '100px' : '120px'};
        line-height: 26px;
        border-radius: 6px;
        background-color: ${({ theme }) => theme.colors.main};
        color: #ffffff;
        font-weight: ${({ theme }) => theme.typo.regular};
        font-size: 16px;
        margin-right: 8px;
    } 

    & > button:first-child {
        margin-right: ${({ hasSecondButton }) => hasSecondButton ? '8px' : '0'};
    }

    & > button:last-child {
        margin-right: 0;
    }

`
export default Modal;
