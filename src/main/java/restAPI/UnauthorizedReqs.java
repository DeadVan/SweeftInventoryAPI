package restAPI;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import static aquality.selenium.browser.AqualityServices.getLogger;
import static utils.DataReader.*;

public class UnauthorizedReqs {
    public static int unauthorizedGetRequests(String endpoint) {
        getLogger().info("sending unauthorized get requests on " + endpoint + " endpoint.");
        Response response = RestAssured.given()
                .get(getBaseUrl() + endpoint);
        if (response.getStatusCode() != 401){
            getLogger().warn("Unauthorized request doesn't have 401 status code! ENDPOINT IS __ " + endpoint);
        }
        return response.statusCode();
    }
    public static int unauthorizedPuttRequests(String endpoint){
        getLogger().info("sending unauthorized put requests on " + endpoint + " endpoint.");
        Response response = RestAssured.given()
                .put(getBaseUrl() + endpoint);
        if (response.getStatusCode() != 401){
            getLogger().warn("Unauthorized request doesn't have 401 status code! ENDPOINT IS __ " + endpoint);
        }
        return response.statusCode();
    }
}
