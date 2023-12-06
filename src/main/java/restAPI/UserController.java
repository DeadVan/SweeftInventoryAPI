package restAPI;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import static aquality.selenium.browser.AqualityServices.getLogger;
import static dto.LoginCrd.loginCrd;
import static utils.DataReader.*;

public class UserController {
    private static final ObjectMapper mapper = new ObjectMapper();

    public static Response postLogin() {
        getLogger().info("Sending post request for authorization");
        try {
            String jsonString = mapper.writeValueAsString(loginCrd);
            Response response = RestAssured.given()
                    .header("Content-Type", ContentType.JSON)
                    .header("origin","admin")
                    .body(jsonString)
                    .post(getBaseUrl() + getLoginPostEndpoint());

            JsonNode jsonNode = mapper.readTree(response.body().asString());
            loginCrd.setAccessToken(jsonNode.get("accessToken").asText());

            return response;
        } catch (JsonProcessingException e) {
            getLogger().error(e.getMessage());
            throw new RuntimeException("error while Processing Json");
        }
    }
    public static Response getUserView() {
        getLogger().info("sending get request for user view");
        return RestAssured.given()
                .header("Authorization","Bearer " + loginCrd.getAccessToken())
                .get(getBaseUrl() + getUserViewEndpoint());
    }

    public static Response getUserList(){
        getLogger().info("sending get request for user List");
        return RestAssured.given()
                .header("Authorization","Bearer " + loginCrd.getAccessToken())
                .queryParam("userRole",getTestData("filter_role"))
                .queryParam("activeStatus",getTestData("filter_status"))
                .get(getBaseUrl()+getUserListEndpoint());
    }
}
