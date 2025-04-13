package com.springboot.sso_main.security;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.util.StringUtils;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLEncoder;

public class JustFilter extends AbstractAuthenticationProcessingFilter {
    private static final AntPathRequestMatcher SSO_MATCHER = new AntPathRequestMatcher("/login" , "POST");

    public JustFilter(AuthenticationManager authenticationManager) {
        super(SSO_MATCHER, authenticationManager);

        // 커스텀 성공 핸들러 설정
        this.setAuthenticationSuccessHandler((request, response, authentication) -> {
            String redirectUrl = request.getParameter("redirect");
            if (StringUtils.hasText(redirectUrl)) {
                redirectUrl += "?token=" + URLEncoder.encode(authentication.getCredentials().toString(), "UTF-8");
                response.sendRedirect(redirectUrl);
            } else {
                response.sendRedirect("/");
            }
        });
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
            throws AuthenticationException {
        if (!request.getMethod().equals("POST")) {
            throw new AuthenticationServiceException("Authentication method not supported: " + request.getMethod());
        }
        String username = request.getParameter("username");
        username = (username != null) ? username.trim() : "";

        String password = request.getParameter("password");
        password = (password != null) ? password : "";

        UsernamePasswordAuthenticationToken authRequest = new UsernamePasswordAuthenticationToken(username, password);
        return this.getAuthenticationManager().authenticate(authRequest);
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response,
                                            FilterChain chain, Authentication authResult)
            throws IOException, ServletException {
        super.successfulAuthentication(request, response, chain, authResult);
    }
}
