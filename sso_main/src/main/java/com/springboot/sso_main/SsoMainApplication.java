package com.springboot.sso_main;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;

@SpringBootApplication
@EnableRedisHttpSession
@EnableAsync
public class SsoMainApplication {

    public static void main(String[] args) {
        SpringApplication.run(SsoMainApplication.class, args);
    }

}
