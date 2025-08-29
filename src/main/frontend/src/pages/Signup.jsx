import { useState } from 'react';
import { signup } from '../api/auth';

export default function Signup(){
  const [username,setU] = useState(''); const [email,setE]=useState(''); const [password,setP]=useState('');

  const onSubmit = async (e) => {
    e.preventDefault();
    const res = await signup({ username, email, password });
    if (res.ok) { alert('회원가입 성공'); window.location.href='/login'; }
    else alert(await res.text() || '회원가입 실패');
  };

  return (
    <form onSubmit={onSubmit} style={{maxWidth:380,margin:'40px auto'}}>
      <input placeholder="아이디" value={username} onChange={e=>setU(e.target.value)} required/>
      <input type="email" placeholder="이메일" value={email} onChange={e=>setE(e.target.value)} required/>
      <input type="password" placeholder="비밀번호" value={password} onChange={e=>setP(e.target.value)} required/>
      <button>회원가입</button>
    </form>
  );
}
