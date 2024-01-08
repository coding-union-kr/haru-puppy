// import axios, { AxiosRequestConfig } from 'axios';
// import cookies from 'js-cookie';

// export const checkToken = async (config: AxiosRequestConfig) => {
//   let accessToken = cookies.get('access_token');
//   // const decode = jwt.decode(accessToken);
//   const nowDate = new Date().getTime() / 1000;
  
//   // 토큰 만료시간이 지났다면
//   if (decode.exp < nowDate) {
//     const { data } = await axios.post(`/token`, { accessToken }, {
//         headers: {
//           access_token: accessToken,
//         },
//       });
//     // 리프레쉬 토큰 발급 서버 요청
    
//     const { refreshToken } = data.data;
    
//     accessToken = refreshToken;
//   }
  
//   config.headers['access_token'] = accessToken;
//   return config; 
// }