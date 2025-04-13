package com.springboot.sso_main.security;

import com.springboot.sso_main.user.UserDetail;
import com.springboot.sso_main.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;

import java.util.ArrayList;

public class SSOAuthProvider implements AuthenticationProvider {

    @Autowired
    private UserService userService;

    // 인증객체를 사용하여 인증을 수행
    @Override
    public Authentication authenticate(Authentication authentication) {
        String username = authentication.getName();  // 사용자 이름 얻기
        String password = (String) authentication.getCredentials();  // 비밀번호 얻기

        UserDetail userDetails = userService.getUserDetails("");
        // 토큰, 유저정보 (토큰 X), 권한정보
        return new SSOToken("token", userDetails, new ArrayList<>());
    }

    // 인증객체가 지원하는지 확인
    @Override
    public boolean supports(Class<?> authentication) {
        return UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication);
        // return SSOToken.class.isAssignableFrom(authentication);
    }
}
