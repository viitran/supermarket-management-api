package com.example.supermarketmanagementapi.filter;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.example.supermarketmanagementapi.cookie.SameSiteHttpServletResponse;
import com.example.supermarketmanagementapi.repository.IAccountRepository;
import com.example.supermarketmanagementapi.service.JwtUserDetailsService;
import com.example.supermarketmanagementapi.utils.ConstantUtils;
import jakarta.servlet.FilterChain;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class CustomAuthorizationFilter extends OncePerRequestFilter {
    @Autowired
    private JwtUserDetailsService jwtUserDetailsService;

    @Autowired
    private IAccountRepository iAccountRepository;

    private final String SECRET;
    private final String PREFIX_VALUE;
    private final String[] PUBLIC_URL;
    private final Integer JWT_TOKEN_VALIDITY;

    public CustomAuthorizationFilter(String SECRET, String PREFIX_VALUE, String jwtTokenValidity, String[] publicUrl) {
        this.SECRET = SECRET;
        this.PREFIX_VALUE = PREFIX_VALUE;
        this.JWT_TOKEN_VALIDITY = Integer.valueOf(jwtTokenValidity);
        this.PUBLIC_URL = publicUrl;
    }

    private Boolean isPublicUrl(String path) {
        for (String string : PUBLIC_URL) {
            if (path.startsWith(string)) {
                return true;
            }
        }
        return false;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws IOException {
        try {
            String servletPath = request.getServletPath();
            if (isPublicUrl(servletPath)) {
                filterChain.doFilter(request, response);
                return;
            }

            Cookie accessCookie = getCookieByName(request, ConstantUtils.Authentication.ACCESS_TOKEN);
//            Cookie refreshCookie = getCookieByName(request, ConstantUtils.Authentication.REFRESH_TOKEN);
            String prefixTokenHeader = request.getHeader(ConstantUtils.Authentication.PREFIX_TOKEN);

            if (isValidPrefix(prefixTokenHeader)) {
                if (accessCookie != null) {
                    handleAccessToken(accessCookie, request, response, filterChain);
                    return;
                }
//                else if (refreshCookie != null) {
//                    handleRefreshToken(refreshCookie, request, response);
//                    return;
//                }
            }

            filterChain.doFilter(request, response);
        } catch (Exception exception) {
            handleFilterException(exception, response);
        }
    }

    private Cookie getCookieByName(HttpServletRequest request, String cookieName) {
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            return Arrays.stream(cookies)
                    .filter(cookie -> cookie.getName().equals(cookieName))
                    .findFirst()
                    .orElse(null);
        }
        return null;
    }

    private boolean isValidPrefix(String prefixTokenHeader) {
        return prefixTokenHeader != null && prefixTokenHeader.startsWith(PREFIX_VALUE);
    }

    private void handleAccessToken(Cookie accessCookie, HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws Exception {
        String token = accessCookie.getValue();
        Algorithm algorithm = Algorithm.HMAC256(SECRET.getBytes());
        JWTVerifier verifier = JWT.require(algorithm).build();
        DecodedJWT decodedJWT = verifier.verify(token);
        String username = decodedJWT.getSubject();
        if (jwtUserDetailsService.loadUserByUsername(username) == null) {
            throw new Exception();
        }
        List<SimpleGrantedAuthority> authorities = Arrays.stream(decodedJWT.getClaim(ConstantUtils.Authentication.ROLES).asArray(String.class))
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());
        UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                username,
                null,
                authorities);
        SecurityContextHolder.getContext().setAuthentication(authToken);
        filterChain.doFilter(request, response);
    }

    private void handleFilterException(Exception exception, HttpServletResponse response) throws IOException {
        clearAuthenticationCookies(response);
        response.setHeader(ConstantUtils.Authentication.ERROR, exception.getMessage());
        response.sendError(HttpStatus.FORBIDDEN.value());
    }

    private void clearAuthenticationCookies(HttpServletResponse response) {
        deleteCookie(response, ConstantUtils.Authentication.ACCESS_TOKEN);
//        deleteCookie(response, ConstantUtils.Authentication.REFRESH_TOKEN);
    }

    private void deleteCookie(HttpServletResponse response, String cookieName) {
        HttpServletResponse res = new SameSiteHttpServletResponse(response);
        Cookie cookie = new Cookie(cookieName, null);
        cookie.setPath("/");
        cookie.setMaxAge(0);
        res.addCookie(cookie);
    }
}
