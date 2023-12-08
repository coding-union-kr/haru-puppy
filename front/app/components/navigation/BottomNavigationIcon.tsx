import React, { ReactNode, useEffect, useState } from 'react';
import Link from 'next/link';
import { usePathname } from 'next/navigation';
import styled  from "styled-components";

interface IBottomNavigationIcon {
    icon: ReactNode;
    title: string;
    route: string;
}

const BottomNavigationIcon = ({icon, title, route}: IBottomNavigationIcon) => {
    const [isActive, setIsActive] = useState(false);
    const pathname = usePathname(); 

    useEffect(() => {
        setIsActive(pathname === route);
    }, [pathname, route]);

   return (
      
    <Link href={route} >
        <IconWrap isActive={isActive}> 
         {icon}
         <strong>{title}</strong> 
           </IconWrap>
    </Link>

   )
};

const IconWrap = styled.li<{ isActive: boolean }>`
    display: flex;
    flex-direction: column;
    justify-content: center;
    align-items: center;
    width: 84px;
    height: 60px;
    color: ${({ theme, isActive }) => isActive ? theme.colors.main : theme.colors.black80};

    &:hover {
        color: ${({theme}) => theme.colors.main};
    }

   & > strong {
        margin-top: 4px;
        font-size: 10px;
        font-weight: ${({ theme }) => theme.typo.regular};
   }
`
 export default BottomNavigationIcon; 