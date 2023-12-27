package dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import static utils.DataReader.*;


@Data
@AllArgsConstructor
public class LoginCrd {
    private String email;
    private String password;
    private Boolean rememberMe;
    private String accessToken;

    public static LoginCrd loginCrd = new LoginCrd(getTestData("user_email"), getTestData("user_password"), true);

    public LoginCrd(String email, String password, boolean rememberMe) {
        this.email = email;
        this.password = password;
        this.rememberMe = rememberMe;
    }
}
