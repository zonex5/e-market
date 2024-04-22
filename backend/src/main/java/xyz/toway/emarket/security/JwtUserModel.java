package xyz.toway.emarket.security;

import lombok.Data;

@Data
public class JwtUserModel {
    private String username;
    private String password;
}
