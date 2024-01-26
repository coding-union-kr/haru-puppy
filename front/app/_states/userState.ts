import { atom } from 'recoil';
import { recoilPersist } from 'recoil-persist';

const { persistAtom } = recoilPersist({
  key: 'userState',
  storage: localStorage,
});

export const userState = atom({
  key: 'userState',
  default: {
    allowNotification: null,
    dogId: '',
    email: '',
    homeId: '',
    imgUrl: null,
    nickname: '',
    userId: '',
    userRole: null,
  },
  effects_UNSTABLE: [persistAtom],
});
