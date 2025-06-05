package org.stefan.media_app.services.impl;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
public class LogoutUtil {

    public static final String EMPTY_PATH = "/";
    public static final String SESSION = "JSESSIONID";

    public void logout(HttpServletRequest request, HttpServletResponse response) {
        request.getSession().invalidate();
        SecurityContextHolder.clearContext();
        Cookie cookie = new Cookie(SESSION, null);
        cookie.setHttpOnly(true);
        cookie.setSecure(true);
        cookie.setPath(EMPTY_PATH);
        cookie.setMaxAge(0);
        response.addCookie(cookie);
    }
}