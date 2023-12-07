"use client"

import { theme } from '@/styles/theme'
import './globals.css'
import { ThemeProvider } from 'styled-components'
import StyledComponentsRegistry from './registry'
import { Pretendard } from '@/public/fonts/fonts'



export default function RootLayout({
  children,
}: {
  children: React.ReactNode
}) {
  return (
    <StyledComponentsRegistry>
      <html lang="en">
        <body className={Pretendard.className}>
          <ThemeProvider theme={theme}>
            {children}
          </ThemeProvider>
        </body>
      </html>
    </StyledComponentsRegistry>
  )
}