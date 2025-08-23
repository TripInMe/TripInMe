import { useEffect, useState } from "react";

export default function App() {
  const [api, setApi] = useState("loading...");
  const [count, setCount] = useState(0);

  useEffect(() => {
    fetch("/api/ping")
      .then(r => r.text())
      .then(setApi)
      .catch(() => setApi("ERROR"));
  }, []);

  return (
    <div style={{fontFamily:"sans-serif", padding:20}}>
      <h1>React OK (CRA)</h1>
      <p>API: <b>{api}</b></p>
      <button onClick={() => setCount(c => c + 1)}>+1</button>
      <span style={{marginLeft:8}}>count: {count}</span>
    </div>
  );
}
