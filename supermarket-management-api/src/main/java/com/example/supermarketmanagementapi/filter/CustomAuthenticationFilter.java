package com.example.supermarketmanagementapi.filter;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.example.supermarketmanagementapi.cookie.SameSiteHttpServletResponse;
import com.example.supermarketmanagementapi.utils.ConstantUtils;
import jakarta.servlet.FilterChain;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.io.BufferedReader;
import java.io.IOException;
import java.sql.Date;
import java.util.Collection;
import java.util.stream.Collectors;

public class CustomAuthenticationFilter  extends UsernamePasswordAuthenticationFilter {
    public static final String USERNAME = "username";
    public static final String PASSWORD = "password";
    private final AuthenticationManager authenticationManager;

    private final String SECRET;
    private final String PREFIX_VALUE;
    private final Integer JWT_TOKEN_VALIDITY;

    public CustomAuthenticationFilter(AuthenticationManager authenticationManager, String secret, String prefixValue,
                                      String jwtTokenValidity) {
        this.authenticationManager = authenticationManager;
        this.SECRET = secret;
        this.PREFIX_VALUE = prefixValue;
        this.JWT_TOKEN_VALIDITY = Integer.valueOf(jwtTokenValidity);
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
            throws AuthenticationException {
        try {
            BufferedReader reader = request.getReader();
            StringBuilder requestData = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                requestData.append(line);
            }
            JSONObject loginInfo = new JSONObject(requestData.toString());
            String username = loginInfo.getString(USERNAME);
            String password = loginInfo.getString(PASSWORD);
            UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(username, password);
            return authenticationManager.authenticate(authenticationToken);
        } catch (IOException | JSONException ex) {
            throw new AuthenticationServiceException("Error reading request data", ex);
        }
    }


    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response,
                                            FilterChain chain, Authentication authResult) {
        User user = (User) authResult.getPrincipal();
        long expirationTime = System.currentTimeMillis() + JWT_TOKEN_VALIDITY;

        String accessToken = generateToken(request, user.getUsername(), expirationTime, user.getAuthorities());
//        String refreshToken = generateToken(request, user.getUsername(), expirationTime, user.getAuthorities());

//        generateCookie(response, refreshToken, ConstantUtils.Authentication.REFRESH_TOKEN);
        generateCookie(response, accessToken, ConstantUtils.Authentication.ACCESS_TOKEN);

        response.addHeader(ConstantUtils.Authentication.PREFIX_TOKEN, PREFIX_VALUE);
    }

    private String generateToken(HttpServletRequest request, String username, long expirationTime, Collection<? extends GrantedAuthority> authorities) {
        return JWT.create()
                .withSubject(username)
                .withExpiresAt(new Date(expirationTime))
                .withIssuer(request.getRequestURL().toString())
                .withClaim(ConstantUtils.Authentication.ROLES, authorities.stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList()))
                .sign(Algorithm.HMAC256(SECRET.getBytes()));
    }


    private void generateCookie(HttpServletResponse response, String token, String cookieName) {
        HttpServletResponse res = new SameSiteHttpServletResponse(response);
        Cookie refreshCookie = new Cookie(cookieName, token);
        refreshCookie.setMaxAge(this.JWT_TOKEN_VALIDITY);
        res.addCookie(refreshCookie);
    }
}
