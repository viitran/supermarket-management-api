package com.example.supermarketmanagementapi.config;

import com.example.supermarketmanagementapi.cookie.SameSiteHttpServletResponse;
import com.example.supermarketmanagementapi.filter.CustomAuthenticationFilter;
import com.example.supermarketmanagementapi.filter.CustomAuthorizationFilter;
import com.example.supermarketmanagementapi.utils.ConstantUtils;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.annotation.Order;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
@PropertySource("classpath:application.properties")
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig {

    private UserDetailsService userDetailsService;

    @Value("${allow.origins}")
    private String allowOrigins;

    Environment env;

    private final String[] PUBLIC_URL = {"/register", "/public/**", "/login"};
    private final String[] IGNORE_URL = {};

    @Autowired
    public WebSecurityConfig(UserDetailsService jwtUserDetailsService, Environment env) {
        this.userDetailsService = jwtUserDetailsService;
        this.env = env;
    }


    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(new BCryptPasswordEncoder());
    }

    @Bean
    @Order(1)
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.cors((httpSecurityCorsConfigurer -> {
            httpSecurityCorsConfigurer.configurationSource(corsConfigurationSource());
        }));
        http.csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(authorizationManagerRequestMatcherRegistry ->
                        authorizationManagerRequestMatcherRegistry.requestMatchers(HttpMethod.DELETE).hasRole("ADMIN")
                                .requestMatchers("/admin/**").hasRole("ADMIN")
                                .requestMatchers("/user/**").hasAnyRole("USER", "ADMIN")
                                .requestMatchers(HttpMethod.POST).permitAll()
                                .requestMatchers(this.PUBLIC_URL).permitAll()
                                .anyRequest().authenticated())
                .httpBasic(Customizer.withDefaults())
                .sessionManagement(httpSecuritySessionManagementConfigurer ->
                        httpSecuritySessionManagementConfigurer.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
        http.logout((httpSecurityLogoutConfigurer -> {
            httpSecurityLogoutConfigurer.logoutSuccessHandler((request, response, authentication) -> {
                        clearAuthenticationCookies(response);
                        response.setStatus(HttpStatus.OK.value());
                    })
                    .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                    .clearAuthentication(true).deleteCookies(ConstantUtils.Authentication.ACCESS_TOKEN);
        }));

        http.with(new CustomFilter(), customFilter -> {
        });
        return http.build();
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

    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        return web -> web.debug(true).ignoring().requestMatchers(this.IGNORE_URL);
    }

    private CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        String[] allowOriginsArr = allowOrigins.split(",");
        List<String> allowOrigins = Arrays.asList(allowOriginsArr);
        configuration.setAllowedOriginPatterns(allowOrigins);
        configuration.setAllowedMethods(Collections.singletonList("*"));
        configuration.setAllowedHeaders(Collections.singletonList("*"));
        configuration.setAllowCredentials(true);
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }

    public class CustomFilter extends AbstractHttpConfigurer<CustomFilter, HttpSecurity> {
        @Override
        public void configure(HttpSecurity http) {
            AuthenticationManager authenticationManager = http.getSharedObject(AuthenticationManager.class);
            http.addFilter(new CustomAuthenticationFilter(
                    authenticationManager,
                    env.getProperty("jwt.secret"),
                    env.getProperty("prefix.value"),
                    env.getProperty("jwt.token.validity")));
            http.addFilterBefore(authorizationFilter(), UsernamePasswordAuthenticationFilter.class);
        }
    }

    @Bean
    public CustomAuthorizationFilter authorizationFilter() {
        return new CustomAuthorizationFilter(
                env.getProperty("jwt.secret"),
                env.getProperty("prefix.value"),
                env.getProperty("jwt.token.validity"),
                PUBLIC_URL);
    }

}
