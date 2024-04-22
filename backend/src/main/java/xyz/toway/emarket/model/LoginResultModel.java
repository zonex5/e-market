package xyz.toway.emarket.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import xyz.toway.emarket.security.LoginStatusEnum;

@Data
@AllArgsConstructor
public class LoginResultModel {
    private String token;
    private String firstName;
    private String lastName;
    private String uuid;
    private boolean isAdmin;
    private LoginStatusEnum loginResult;

    public LoginResultModel(LoginStatusEnum loginResult) {
        this.loginResult = loginResult;
    }
}
