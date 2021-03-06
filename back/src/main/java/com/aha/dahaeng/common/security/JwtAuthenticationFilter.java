package com.aha.dahaeng.common.security;


import com.aha.dahaeng.common.security.jwt.JwtProperties;
import com.aha.dahaeng.common.security.jwt.JwtProvider;
import com.aha.dahaeng.common.security.jwt.JwtToken;
import com.aha.dahaeng.user.domain.User;
import com.aha.dahaeng.user.dto.request.LoginRequest;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;

/**
 * com.aha.dahaeng.common.security
 * JwtAuthenticationFilter.java
 *
 * @author 이주희
 * @date 2021-04-22 오후 3:06
 * @변경이력
 **/

public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    public JwtAuthenticationFilter(AuthenticationManager authenticationManager) {
        super.setAuthenticationManager(authenticationManager);
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        UsernamePasswordAuthenticationToken authenticationToken = null;
        try {
            LoginRequest loginRequest = new ObjectMapper().readValue(request.getInputStream(), LoginRequest.class);
            authenticationToken = new UsernamePasswordAuthenticationToken(loginRequest.getLoginId(), loginRequest.getPassword(), new ArrayList<>());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return this.getAuthenticationManager().authenticate(authenticationToken);
    }


    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authentication) throws IOException, ServletException {
        User user = (User) authentication.getPrincipal();
        JwtToken jwtToken = JwtProvider.createBody(user);

        response.addHeader(JwtProperties.HEADER_STRING, jwtToken.getAccessToken());
        response.addHeader(JwtProperties.REFRESH_HEADER_STRING, jwtToken.getRefreshToken());
    }
}
