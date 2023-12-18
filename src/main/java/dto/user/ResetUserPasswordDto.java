package dto.user;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import static dto.LoginCrd.loginCrd;
import static utils.DataReader.getTestData;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ResetUserPasswordDto {

    public ResetUserPasswordDto(String oldPassword, String newPassword) {
        this.oldPassword = oldPassword;
        this.newPassword = newPassword;
    }

    private String email;
    private String oldPassword;
    private String newPassword;
    private String uniqueString;

    public static ResetUserPasswordDto resetUserPasswordDto = new ResetUserPasswordDto(loginCrd.getPassword(),getTestData("user_new_password"));


}
