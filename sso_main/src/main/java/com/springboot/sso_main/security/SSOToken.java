package com.springboot.sso_main.security;

import com.springboot.sso_main.user.UserDetail;
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

    public SSOToken(String credential, UserDetail principal, Collection<? extends GrantedAuthority> authorities) {
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
