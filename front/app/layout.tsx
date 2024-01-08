"use client"
import Script from 'next/script'
import { theme } from '@/styles/theme'
import './globals.css'
import { ThemeProvider } from 'styled-components'
import StyledComponentsRegistry from './registry'
import { Pretendard } from '@/public/fonts/fonts'
import { QueryClientProvider, QueryClient } from 'react-query'

export default function RootLayout({
  children,
}: {
  children: React.ReactNode
}) {
  const queryClient = new QueryClient()

  return (
    <QueryClientProvider client={queryClient}>
      <StyledComponentsRegistry>
        <html lang="kr">
          <head>
            <Script
              src="https://t1.kakaocdn.net/kakao_js_sdk/2.6.0/kakao.min.js"
              strategy="afterInteractive"
              onLoad={() => {
                if (window.Kakao && !window.Kakao.isInitialized()) {
                  window.Kakao.init('281badc9872f0dabb0a6ffdaedda3fe5');
                  console.log('Kakao SDK 로드하고 초기화됨');
                }
              }}
            />
          </head>
          <body className={Pretendard.className}>
            <ThemeProvider theme={theme}>
              {children}
            </ThemeProvider>
          </body>
        </html>
      </StyledComponentsRegistry>
    </QueryClientProvider>

  )
}