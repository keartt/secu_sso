package com.springboot.sso_main.user;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.io.Serial;
import java.io.Serializable;

@Getter
@Setter
@Builder
public class UserDetail implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;
    private String token;

    private String userId;
    private String username;
    private String name;
    private String email;
    private String company;
    private String phone;
    private String a_type;

}
