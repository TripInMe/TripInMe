import { useState } from 'react';
import { login } from '../api/auth';

export default function Login(){
  const [usernameOrEmail, setU] = useState('');
  const [password, setP] = useState('');

  const onSubmit = async (e) => {
    e.preventDefault();
    const res = await login({ usernameOrEmail, password });
    if (res.ok) window.location.href = '/';
    else alert(await res.text() || '로그인 실패');
  };

  return (
    <form onSubmit={onSubmit} style={{maxWidth:380,margin:'40px auto'}}>
      <input placeholder="아이디 또는 이메일" value={usernameOrEmail} onChange={e=>setU(e.target.value)} required/>
      <input type="password" placeholder="비밀번호" value={password} onChange={e=>setP(e.target.value)} required/>
      <button>로그인</button>
    </form>
  );
}
