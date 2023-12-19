package restAPI;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import dto.user.ResetUserPasswordDto;
import dto.user.UserDto;
import io.qameta.allure.Step;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import utils.StatusCode;

import java.util.Scanner;

import static aquality.selenium.browser.AqualityServices.getLogger;
import static dto.LoginCrd.loginCrd;
import static dto.user.ResetUserPasswordDto.resetUserPasswordDto;
import static utils.DataReader.*;
import static utils.RandUtils.generateString;

public class UserReqs {
    private static final ObjectMapper mapper = new ObjectMapper();
    private static UserDto registerUser = new UserDto();

    @Step("Perform login and get access token")
    public static Response postLogin() {
        getLogger().info("Sending POST request for authorization");
        try {
            String jsonString = mapper.writeValueAsString(loginCrd);
            Response response = RestAssured.given()
                    .header("Content-Type", ContentType.JSON)
                    .header("origin", "admin")
                    .body(jsonString)
                    .post(getBaseUrl() + getEndPoint("login_post"));

            JsonNode jsonNode = mapper.readTree(response.body().asString());
            loginCrd.setAccessToken(jsonNode.get("accessToken").asText());
            if (response.getStatusCode() != StatusCode.OK.getCode()){
                getLogger().error(response.getBody().asString() + response.getStatusCode());
            }
            return response;
        } catch (JsonProcessingException e) {
            getLogger().error(e.getMessage());
            throw new RuntimeException("error while Processing Json");
        }
    }

    @Step("Get user view")
    public static Response getUserView() {
        getLogger().info("sending GET request for user view");
        Response response = RestAssured.given()
                .header("Authorization", "Bearer " + loginCrd.getAccessToken())
                .get(getBaseUrl() + getEndPoint("userView_get"));
        if (response.getStatusCode() != StatusCode.OK.getCode()){
            getLogger().error(response.getBody().asString());
        }
        return response;
    }

    @Step("Get user list with filters")
    public static Response getUserList() {
        getLogger().info("sending GET request for user List");
        Response response = RestAssured.given()
                .header("Authorization", "Bearer " + loginCrd.getAccessToken())
                .queryParam("userRole", getTestData("filter_role"))
                .queryParam("activeStatus", getTestData("filter_status"))
                .get(getBaseUrl() + getEndPoint("userList_get"));
        if (response.getStatusCode() != StatusCode.OK.getCode()){
            getLogger().error(response.getBody().asString());
        }
        return response;
    }

    @Step("Register a new user")
    public static Response registerUser() {
        registerUser.setFirstName(generateString(7, 0, 0));
        registerUser.setLastName(generateString(7, 0, 0));
        registerUser.setEmail(getTestData("sweeft_mail"));
        registerUser.setPhoneNumber("+995 593" + generateString(6, 0, 6));
        registerUser.setUserRole(getTestData("filter_role"));
        registerUser.setPermissionList(new String[]{getTestData("validator")});
        getLogger().info("sending POST request for user registration Credentials - " + registerUser);
        try {
            String jsonString = mapper.writeValueAsString(registerUser);
            Response response = RestAssured.given()
                    .header("Authorization", "Bearer " + loginCrd.getAccessToken())
                    .contentType(ContentType.JSON)
                    .body(jsonString)
                    .post(getBaseUrl() + getEndPoint("user_register_post"));
            getLogger().warn(response.getBody().asString());
            JsonNode jsonNode = mapper.readTree(response.body().asString());
            registerUser.setId(jsonNode.get("id").asInt());
            if (response.getStatusCode() != StatusCode.CREATED.getCode()){
                getLogger().error(response.getBody().asString() + response.getStatusCode());
            }
            return response;
        } catch (JsonProcessingException e) {
            getLogger().error(e.getMessage());
            throw new RuntimeException("error while JsonProcessing");
        }
    }

    @Step("Delete registered user")
    public static Response deleteUser() {
        getLogger().info("sending DELETE request for user. info - " + registerUser);
        Response response = RestAssured.given()
                .header("Authorization", "Bearer " + loginCrd.getAccessToken())
                .delete(getBaseUrl() + getEndPoint("user_delete") + registerUser.getId());
        if (response.getStatusCode() != StatusCode.OK.getCode()){
            getLogger().error(response.getBody().asString() + response.getStatusCode());
        }
        return response;
    }

    @Step("Logout user")
    public static Response logoutUser() {
        getLogger().info("sending POST request to logout user");
        Response response = RestAssured.given()
                .header("Authorization", "Bearer " + loginCrd.getAccessToken())
                .post(getBaseUrl() + getEndPoint("logout_post"));
        if (response.getStatusCode() != StatusCode.OK.getCode()){
            getLogger().error(response.getBody().asString() + response.getStatusCode());
        }
        return response;
    }

    @Step("Reset user password")
    public static Response changePassword() {
        getLogger().info("sending PUT request to change password");
        try {
            String jsonString = mapper.writeValueAsString(resetUserPasswordDto);
            Response response = RestAssured.given()
                    .header("Authorization", "Bearer " + loginCrd.getAccessToken())
                    .contentType(ContentType.JSON)
                    .body(jsonString)
                    .put(getBaseUrl() + getEndPoint("user_resetPassword_put"));
            if (response.getStatusCode() != StatusCode.OK.getCode()){
                getLogger().error(response.getBody().asString()+ response.getStatusCode());
            }
            return response;
        } catch (JsonProcessingException e) {
            getLogger().error(e.getMessage());
            throw new RuntimeException("error while processing Json");
        }
    }

    @Step("Restore forgotten password")
    public static Response resetPassword() {
        getLogger().info("sending PUT request to reset password as it was");
        String changedPassword = resetUserPasswordDto.getNewPassword();
        resetUserPasswordDto.setOldPassword(changedPassword);
        resetUserPasswordDto.setNewPassword(loginCrd.getPassword());
        try {
            String jsonString = mapper.writeValueAsString(resetUserPasswordDto);
            Response response = RestAssured.given()
                    .header("Authorization", "Bearer " + loginCrd.getAccessToken())
                    .contentType(ContentType.JSON)
                    .body(jsonString)
                    .put(getBaseUrl() + getEndPoint("user_resetPassword_put"));
            if (response.getStatusCode() != StatusCode.OK.getCode()){
                getLogger().error(response.getBody().asString()+ response.getStatusCode());
            }
            return response;
        } catch (JsonProcessingException e) {
            getLogger().error(e.getMessage());
            throw new RuntimeException("error while processing Json");
        }
    }

    public static Response restoreForgottenPass() {
        getLogger().info("sending PUT request to restore forgotten password");
        Scanner scanner = new Scanner(System.in);
        String mailResetPasswordUniqueString = scanner.nextLine();
        resetUserPasswordDto.setEmail(loginCrd.getEmail());
        resetUserPasswordDto.setNewPassword(loginCrd.getPassword());
        resetUserPasswordDto.setUniqueString(mailResetPasswordUniqueString);
        try {
            String jsonString = mapper.writeValueAsString(resetUserPasswordDto);
            Response response =  RestAssured.given()
                    .contentType(ContentType.JSON)
                    .body(jsonString)
                    .put(getBaseUrl() + getEndPoint("user_restoreForgottenPass_put"));
            if (response.getStatusCode() != StatusCode.OK.getCode()){
                getLogger().error(response.getBody().asString()+ response.getStatusCode());
            }
            return response;
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}
