package com.springboot.sso_client.sso;

import com.springboot.sso_main.security.SSOToken;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.web.bind.ServletRequestUtils;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class SSOFilter extends AbstractAuthenticationProcessingFilter {
    private static final AntPathRequestMatcher SSO_MATCHER = new AntPathRequestMatcher("/ssoLogin" );
    private static final String TOKEN_STRING = "token";

    protected SSOFilter(String defaultFilterProcessesUrl) {
        super(defaultFilterProcessesUrl);
    }

    public SSOFilter(AuthenticationManager authenticationManager) {
        // matcher 로 들어온 요청을 가로채서
        // 주입받은 authenticationManager 에게 인증을 요청한다.
        super(SSO_MATCHER, authenticationManager);

        // 그리고 성공할경우 redirect
        this.setAuthenticationSuccessHandler((req, res, auth) ->
                // res.sendRedirect(Optional.ofNullable(req.getParameter("redirect")).filter(r -> !r.isEmpty()).orElse("/"))
                res.sendRedirect("/home")
        );

    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException, IOException, ServletException {
        // 파라미터로 넘어온 토큰을 가져온다.
        String token = ServletRequestUtils.getStringParameter(request, TOKEN_STRING, "").trim();
        SSOToken authRequest = new SSOToken(token);
        // 해당값으로 인증여부를 수행해준다.
        return this.getAuthenticationManager().authenticate(authRequest);
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {
        // Handle successful authentication
        super.successfulAuthentication(request, response, chain, authResult);
    }
}

