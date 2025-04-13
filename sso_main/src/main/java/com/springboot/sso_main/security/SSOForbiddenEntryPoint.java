package com.springboot.sso_main.security;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.util.StringUtils;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.Optional;

public class SSOForbiddenEntryPoint implements AuthenticationEntryPoint {
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        // 인증되지 않은 사용자가 접근할 경우 처리하는 메소드
        String originalRedirect = request.getParameter("redirect");
        StringBuilder sb = new StringBuilder("http://localhost:8081/login");

        if (originalRedirect != null && !originalRedirect.isEmpty()) {
            sb.append("?redirect=").append(URLEncoder.encode(originalRedirect, "UTF-8"));
        }

        response.sendRedirect(sb.toString());
    }
}
