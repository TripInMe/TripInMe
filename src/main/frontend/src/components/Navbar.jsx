import { Link, useNavigate } from 'react-router-dom';
import { useAuth } from '../context/AuthContext';
import { logout } from '../api/auth';

export default function Navbar() {
  const { user, setUser } = useAuth();
  const nav = useNavigate();

  const onLogout = async () => {
    try {
      await logout();                 // POST /api/auth/logout (credentials 포함은 client.js가 처리)
    } catch (_) {}
    setUser(null);
    nav('/login', { replace: true });
  };

  return (
    <nav style={styles.nav}>
      <Link to="/" style={styles.brand}>TripMeyou</Link>
      <div style={{ flex: 1 }} />
      {user ? (
        <>
          <span style={styles.userText}>{user.username} 님</span>
          <button onClick={onLogout} style={styles.btn}>로그아웃</button>
        </>
      ) : (
        <>
          <Link to="/login" style={styles.link}>로그인</Link>
          <Link to="/signup" style={styles.link}>회원가입</Link>
        </>
      )}
    </nav>
  );
}

const styles = {
  nav: {
    display: 'flex', alignItems: 'center', gap: 12,
    background: '#fff', padding: '10px 16px',
    boxShadow: '0 2px 5px rgba(0,0,0,.06)', marginBottom: 16
  },
  brand: { fontWeight: 800, textDecoration: 'none', color: '#333' },
  link: { textDecoration: 'none', color: '#333', padding: '6px 10px' },
  btn: { background: 'transparent', border: '1px solid #888', padding: '6px 12px', borderRadius: 6, cursor: 'pointer' },
  userText: { color: '#555', marginRight: 8 }
};
