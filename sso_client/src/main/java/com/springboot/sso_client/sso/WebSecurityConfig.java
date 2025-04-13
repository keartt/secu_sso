package com.springboot.sso_client.sso;

import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.ObjectPostProcessor;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.session.CompositeSessionAuthenticationStrategy;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig  extends WebSecurityConfigurerAdapter {
    @Bean
    public SSOAuthProvider ssoAuthenticationProvider() {
        return new SSOAuthProvider();
    }

    @Bean
    public SSOFilter ssoFilter() throws Exception {
        return new SSOFilter(this.authenticationManager());
    }

    @Bean
    public SSOForbiddenEntryPoint ssoForbiddenEntryPoint() {
        return new SSOForbiddenEntryPoint();
    }

    // 정적 리소스 경로는 시큐리티 없이 적용 가능하도록
    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().requestMatchers(PathRequest.toStaticResources().atCommonLocations());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable()
                .authorizeRequests()
                .antMatchers("/login", "/ssologin").permitAll()
                .anyRequest().authenticated()
                // .anyRequest().permitAll()
                .and()
                // 인증되지 않은 사용자가 접근할 경우 처리하는 엔트리 포인트 설정
                .exceptionHandling().authenticationEntryPoint(ssoForbiddenEntryPoint())
                .and()
                .logout()
                .logoutSuccessUrl("/loginPage?logout=true") // 로그아웃 후 이동
                .permitAll();

        // SSOFilter 설정
        final SSOFilter filter = ssoFilter();
        http.addFilterAt(filter, UsernamePasswordAuthenticationFilter.class)
                .authenticationProvider(ssoAuthenticationProvider())
                // 세션설정
                .sessionManagement()
                // .sessionFixation().migrateSession() // 로그인 시 기존 세션 무효화
                // .sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED) // 필요시에만 세션 생성
                // .invalidSessionUrl("/invalidSession") // 세션 만료시 이동 > 세션이 만료되었어요
                .maximumSessions(1).expiredUrl("/invalidSession")// 최대 허용세션1 > 다른 위치에서 로그인해서 세션 종료임
                .and()
                // 시큐리티 세션 전략을 커스텀 필터에도 연결
                .addObjectPostProcessor(new ObjectPostProcessor<CompositeSessionAuthenticationStrategy>() {
                    @Override
                    public <O extends CompositeSessionAuthenticationStrategy> O postProcess(O object) {
                        filter.setSessionAuthenticationStrategy((CompositeSessionAuthenticationStrategy) object);
                        return object;
                    }
                });
    }


}