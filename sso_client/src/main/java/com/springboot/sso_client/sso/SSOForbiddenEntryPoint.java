package com.springboot.sso_client.sso;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class SSOForbiddenEntryPoint implements AuthenticationEntryPoint {
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        // 인증되지 않은 사용자가 접근할 경우 처리하는 메소드
        StringBuilder sb = new StringBuilder("http://localhost:8081/login");
        sb.append("?redirect=http://localhost:8082/ssoLogin");

        response.sendRedirect(sb.toString());
    }
}