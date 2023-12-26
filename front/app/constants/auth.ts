export const LOCAL_STORAGE_KEYS = {
    ACCESS_TOKEN: 'access_token',
    REFRESH_TOKEN: 'refresh_token'
}


const NEXT_PUBLIC_REST_API_KEY = 'bcc039ad09020f3b45a613b68b8b14e6'

//kakao 인증에 필요한 변수


const KAKAO_REST_API_KEY = process.env.NEXT_PUBLIC_REST_API_KEY;

const KAKAO_REDIRECT_URI = "http://localhost:3000/auth/callback/kakao"

export const KAKAO_AUTH_URL = `https://kauth.kakao.com/oauth/authorize?client_id=${NEXT_PUBLIC_REST_API_KEY}&redirect_uri=${KAKAO_REDIRECT_URI}&response_type=code`;
