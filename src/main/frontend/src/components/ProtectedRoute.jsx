import { useEffect } from 'react';
import { useLocation, useNavigate } from 'react-router-dom';
import { useAuth } from '../context/AuthContext';

export default function ProtectedRoute({ children }) {
  const { user, ready } = useAuth();
  const nav = useNavigate();
  const loc = useLocation();

  useEffect(() => {
    if (!ready) return;           // /api/auth/me 확인 전엔 대기
    if (!user) {
      // 로그인 안 했으면 로그인 페이지로 보내고, 돌아올 위치 기억
      nav('/login', { replace: true, state: { from: loc.pathname + loc.search } });
    }
  }, [ready, user, nav, loc]);

  if (!ready) return null;        // 로딩 중
  if (!user) return null;         // 리다이렉트 중
  return children;                // 통과!
}
