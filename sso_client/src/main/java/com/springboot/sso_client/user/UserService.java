package com.springboot.sso_client.user;

import com.springboot.sso_main.user.UserDetail;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    // 사용자 정보를 가져오는 메서드
    public UserDetail getUserDetails(String token) {
        // 디비에서 정보 가져온다
        // 넣어서 리턴한다.
        return UserDetail.builder()
                .username("slo_user")
                .name("홍길동")
                .email("hong@example.com")
                .company("SSO Corp")
                .phone("010-1234-5678")
                .a_type("user")
                .build();
    }

}
