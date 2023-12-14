package restAPI;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import dto.user.UserDto;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import static aquality.selenium.browser.AqualityServices.getLogger;
import static dto.LoginCrd.loginCrd;
import static utils.DataReader.*;
import static utils.RandUtils.generateString;

public class UserReqs {
    private static final ObjectMapper mapper = new ObjectMapper();
    private static UserDto registerUser = new UserDto();


    public static Response postLogin() {
        getLogger().info("Sending POST request for authorization");
        try {
            String jsonString = mapper.writeValueAsString(loginCrd);
            Response response = RestAssured.given()
                    .header("Content-Type", ContentType.JSON)
                    .header("origin","admin")
                    .body(jsonString)
                    .post(getBaseUrl() + getEndPoint("login_post"));

            JsonNode jsonNode = mapper.readTree(response.body().asString());
            loginCrd.setAccessToken(jsonNode.get("accessToken").asText());

            return response;
        } catch (JsonProcessingException e) {
            getLogger().error(e.getMessage());
            throw new RuntimeException("error while Processing Json");
        }
    }
    public static Response getUserView() {
        getLogger().info("sending GET request for user view");
        return RestAssured.given()
                .header("Authorization","Bearer " + loginCrd.getAccessToken())
                .get(getBaseUrl() + getEndPoint("userView_get"));
    }

    public static Response getUserList(){
        getLogger().info("sending GET request for user List");
        return RestAssured.given()
                .header("Authorization","Bearer " + loginCrd.getAccessToken())
                .queryParam("userRole",getTestData("filter_role"))
                .queryParam("activeStatus",getTestData("filter_status"))
                .get(getBaseUrl()+getEndPoint("userList_get"));
    }
    public static Response registerUser(){
        registerUser.setFirstName(generateString(7,0,0));
        registerUser.setLastName(generateString(7,0,0));
        registerUser.setEmail(getTestData("sweeft_mail"));
        registerUser.setPhoneNumber("+995 593" + generateString(6,0,6) );
        registerUser.setUserRole(getTestData("filter_role"));
        registerUser.setPermissionList(new String[]{getTestData("validator")});
        getLogger().info("sending POST request for user registration Credentials - " + registerUser);
        try {
            String jsonString = mapper.writeValueAsString(registerUser);
            Response response =  RestAssured.given()
                    .header("Authorization","Bearer " + loginCrd.getAccessToken())
                    .contentType(ContentType.JSON)
                    .body(jsonString)
                    .post(getBaseUrl() + getEndPoint("user_register"));
            getLogger().warn(response.getBody().asString());
            JsonNode jsonNode = mapper.readTree(response.body().asString());
            registerUser.setId(jsonNode.get("id").asInt());

            return response;
        } catch (JsonProcessingException e) {
            getLogger().error(e.getMessage());
            throw new RuntimeException("error while JsonProcessing");
        }
    }
    public static Response deleteUser(){
        getLogger().info("sending DELETE request for user. info - " + registerUser);
        return RestAssured.given()
                .header("Authorization","Bearer " + loginCrd.getAccessToken())
                .delete(getBaseUrl()+getEndPoint("user_delete")+registerUser.getId());
    }
}
