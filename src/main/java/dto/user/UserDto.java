package dto.user;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UserDto {

    public UserDto(String firstName, String lastName, String email, String phoneNumber, String userRole, String[] permissionList) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.userRole = userRole;
        this.permissionList = permissionList;
    }

    private String firstName;
    private int id;
    private String lastName;
    private String email;
    private String phoneNumber;
    private String userRole;
    private String dbStatus;
    private String[] items;
    private String[] permissionList;

    @JsonCreator
    public UserDto(@JsonProperty("firstName")String firstName,
                   @JsonProperty("id")int id,
                   @JsonProperty("lastName")String lastName,
                   @JsonProperty("email")String email,
                   @JsonProperty("phoneNumber")String phoneNumber,
                   @JsonProperty("userRole")String userRole,
                   @JsonProperty("dbStatus")String dbStatus,
                   @JsonProperty("items")String[] items,
                   @JsonProperty("permissionList")String[] permissionList) {
        this.firstName = firstName;
        this.id = id;
        this.lastName = lastName;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.userRole = userRole;
        this.dbStatus = dbStatus;
        this.items = items;
        this.permissionList = permissionList;
    }

}
