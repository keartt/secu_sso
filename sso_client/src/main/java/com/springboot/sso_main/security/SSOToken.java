package com.springboot.sso_main.security;

import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.SpringSecurityCoreVersion;

import java.io.Serial;
import java.util.ArrayList;
import java.util.Collection;

public class SSOToken extends AbstractAuthenticationToken {
    @Serial
    private static final long serialVersionUID = SpringSecurityCoreVersion.SERIAL_VERSION_UID;
    private final String token;
    private final Object principal;

    // 최초 접속시 사용되는 생성자
    public SSOToken(String token) {
        super(new ArrayList<>());
        this.token = token;
        this.principal = null;
        setAuthenticated(false);
    }
    // 인증 후 사용되는 생성자
    public SSOToken(String credential, Object principal, Collection<? extends GrantedAuthority> authorities) {
        super(authorities);
        this.token = credential;
        this.principal = principal;
        super.setAuthenticated(true);
    }

    @Override
    public Object getCredentials() {
        return token;
    }

    @Override
    public Object getPrincipal() {
        return principal;
    }
}

