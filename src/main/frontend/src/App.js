import { Routes, Route, Navigate } from 'react-router-dom';
import Navbar from './components/Navbar';
import ProtectedRoute from './components/ProtectedRoute';

import Home from './pages/Home';
import Login from './pages/Login';
import Signup from './pages/Signup';

// 게시판 페이지가 생기면 import 후 ProtectedRoute로 감싸주세요.
// import BoardList from './pages/board/List';
// import PostWrite from './pages/board/Write';

export default function App() {
  return (
    <>
      {/* 상단 공통 네비 */}
      <Navbar />

      {/* 페이지 전환 */}
      <Routes>
        <Route path="/" element={<Home />} />
        <Route path="/login"  element={<Login />} />
        <Route path="/signup" element={<Signup />} />

        {/* 예시: 보호 라우트 (로그인 필요) */}
        {/* 
        <Route
          path="/board"
          element={
            <ProtectedRoute>
              <BoardList />
            </ProtectedRoute>
          }
        />
        <Route
          path="/board/write"
          element={
            <ProtectedRoute>
              <PostWrite />
            </ProtectedRoute>
          }
        />
        */}

        <Route path="*" element={<Navigate to="/" replace />} />
      </Routes>
    </>
  );
}
