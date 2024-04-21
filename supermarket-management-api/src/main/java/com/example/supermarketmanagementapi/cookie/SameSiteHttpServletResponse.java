package com.example.supermarketmanagementapi.cookie;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpServletResponseWrapper;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;

public class SameSiteHttpServletResponse extends HttpServletResponseWrapper {
    public SameSiteHttpServletResponse(HttpServletResponse response) {
        super(response);
    }

    @Override
    public void addCookie(Cookie cookie) {
        ResponseCookie responseCookie = ResponseCookie
                .from(cookie.getName(), cookie.getValue())
                .secure(true)
                .httpOnly(true)
                .path(cookie.getPath())
                .maxAge(cookie.getMaxAge())
                .sameSite("None")
                .build();
        this.addHeader(HttpHeaders.SET_COOKIE, responseCookie.toString());
    }
}
