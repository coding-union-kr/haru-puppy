import axios, { AxiosError } from 'axios';

const instance = axios.create({
    baseURL: 'http://localhost:8080',
    headers: {
        'Content-type': 'application/json',
    },
});


instance.interceptors.request.use(
    (config) => {
        console.log('request config', config)
        const accessToken = localStorage.getItem('access-token');

        config.headers['Content-Type'] = 'application/json';
        config.headers['Authorization'] = `Bearer ${accessToken}`;

        return config;
    },
    (error) => {
        console.log(error);
        return Promise.reject(error);
    }
);


instance.interceptors.response.use(
    (response) => {
        if (response.status === 404) {
            console.log('404 페이지로 넘어가야 함!');
        }

        return response;
    },
    async (error) => {
        console.log('interceptor error', error)
        if (error.response?.status === 401 && error.response.data.message === '요청 토큰이 만료되었습니다') {
            try {
                const refreshToken = localStorage.getItem('refresh-token');
                const refreshResponse = await axios.post('/auth/refresh', { refreshToken });

                const { accessToken } = refreshResponse.data;

                error.config.headers = {
                    'Content-Type': 'application/json',
                    Authorization: `Bearer ${accessToken}`,
                };

                // 새로운 토큰으로 요청 재시도
                const response = await axios.request(error.config);
                return response;
            } catch (refreshError) {
                // 리프레시 토큰이 유효하지 않은 경우
                console.error('토큰 리프레시 실패:', refreshError);
                // 로그인 페이지로 리다이렉트 또는 다른 작업 수행
            }
        }
        return Promise.reject(error);
    }
);

export default instance;