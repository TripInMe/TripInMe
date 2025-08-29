package com.example.TripInMe.common;

import jakarta.servlet.http.HttpSession;

public class SessionUtil {
    public static Long requireUserId(HttpSession session){
        Object id = session.getAttribute("USER_ID");
        if(id == null) throw new IllegalStateException("로그인이 필요합니다.");
        return (Long) id;
    }
}
