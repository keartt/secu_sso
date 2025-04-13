package com.springboot.sso_client.sso;


import com.springboot.sso_main.user.UserDetail;
import com.springboot.sso_client.user.UserService;
import com.springboot.sso_main.security.SSOToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;

import java.util.ArrayList;

public class SSOAuthProvider implements AuthenticationProvider {

    @Autowired
    private UserService userService;

    // 인증객체를 사용하여 인증을 수행
    @Override
    public Authentication authenticate(Authentication authentication) {
        String token = (String) authentication.getCredentials();
        UserDetail userDetails = userService.getUserDetails(token);
        // 토큰, 유저정보 (토큰 X), 권한정보
        return new SSOToken(token, userDetails, new ArrayList<>());
    }

    // 인증객체가 지원하는지 확인
    @Override
    public boolean supports(Class<?> authentication) {
        return SSOToken.class.isAssignableFrom(authentication);
    }
}
