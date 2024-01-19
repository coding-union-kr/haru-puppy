import { useEffect } from 'react';
import { RecoilState, useRecoilState } from 'recoil';

export const usePersistentRecoilState = <T>(key: string, atom: RecoilState<T>) => {
  const [state, setState] = useRecoilState(atom);

  // 컴포넌트가 마운트될 때 로컬 스토리지에서 상태 불러오기
  useEffect(() => {
    const savedState = localStorage.getItem(key);
    if (savedState != null) {
      setState(JSON.parse(savedState));
    }
  }, [key, setState]);

  // 상태가 변경될 때마다 로컬 스토리지에 저장
  useEffect(() => {
    localStorage.setItem(key, JSON.stringify(state));
  }, [key, state]);

  return [state, setState];
};
