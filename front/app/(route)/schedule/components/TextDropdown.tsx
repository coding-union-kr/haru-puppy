import styled from "styled-components";

interface ITextDropdownProps {
    options: string[];
    handleSelect: (value: string) => void;
}

const TextDropdown = ({ options, handleSelect } : ITextDropdownProps) => (
    <TextDropdownWrap>
        {options.map(option => (
            <li key={option} onClick={() => handleSelect(option)}>
                {option}
            </li>
        ))}
    </TextDropdownWrap>
);

const TextDropdownWrap = styled.ul`
    width: 150px;
    display: flex;
    flex-direction: column;
    align-items: center;

    & > li {
        position: relative;
        cursor: pointer;
        text-align: center;
        width: 100%;
        font-size: 14px;
        color: ${({theme}) => theme.colors.black90};
        padding: 15px 0;
        &:hover {
            background-color: #F7FBFF;
        }
    }

    & > li:not(:last-child)::after {
        content: ''; 
        position: absolute;
        bottom: 0; 
        left: 0; 
        width: 100%;
        border-bottom: 1px solid ${({theme}) => theme.colors.black60}; 
    }

    & > li:first-child {
        &:hover {
            border-top-right-radius: 10px;
            border-top-left-radius: 10px;
        }
    }

    & > li:last-child {
        &:hover {
            border-bottom-right-radius: 10px;
            border-bottom-left-radius: 10px;
        }
    }
`;

export default TextDropdown;