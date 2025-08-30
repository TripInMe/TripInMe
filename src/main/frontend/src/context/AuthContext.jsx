import { createContext, useContext, useEffect, useState } from 'react';

const AuthCtx = createContext(null);
export const useAuth = () => useContext(AuthCtx);

export default function AuthProvider({ children }){
  const [user,setUser] = useState(null);
  const [ready,setReady] = useState(false);

  useEffect(()=>{
    fetch('/api/auth/me',{credentials:'include'})
      .then(r=>r.ok?r.json():null)
      .then(data=>setUser(data))
      .finally(()=>setReady(true));
  },[]);
  return <AuthCtx.Provider value={{user,setUser,ready}}>{children}</AuthCtx.Provider>;
}
