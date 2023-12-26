package restAPI;

import io.restassured.RestAssured;
import io.restassured.response.Response;

import static aquality.selenium.browser.AqualityServices.getLogger;
import static dto.LoginCrd.loginCrd;
import static utils.DataReader.*;

public class AttributeReqs {

    public static Response getAttribute(int categoryId){
        getLogger().info("sending GET request for attribute");
        Response response = RestAssured.given()
                .header("Authorization", "Bearer " + loginCrd.getAccessToken())
                .get(getBaseUrl() + getEndPoint("attribute_get") + categoryId);
        System.out.println(response.getBody().asString());
        return response;
    }

}
