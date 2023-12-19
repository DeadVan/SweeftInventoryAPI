package restAPI;

import io.qameta.allure.Step;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import static aquality.selenium.browser.AqualityServices.getLogger;
import static utils.DataReader.*;

public class UnauthorizedReqs {

    @Step("Send unauthorized GET requests on endpoint - {endpoint}")
    public static int unauthorizedGetRequests(String endpoint) {
        getLogger().info("sending unauthorized GET requests on " + endpoint + " endpoint.");
        Response response = RestAssured.given()
                .get(getBaseUrl() + endpoint);
        if (response.getStatusCode() != 401){
            getLogger().warn("Unauthorized request doesn't have 401 status code! ENDPOINT IS __ " + endpoint);
        }
        return response.statusCode();
    }

    @Step("Send unauthorized PUT requests on endpoint - {endpoint}")
    public static int unauthorizedPuttRequests(String endpoint){
        getLogger().info("sending unauthorized PUT requests on " + endpoint + " endpoint.");
        Response response = RestAssured.given()
                .put(getBaseUrl() + endpoint);
        if (response.getStatusCode() != 401){
            getLogger().warn("Unauthorized request doesn't have 401 status code! ENDPOINT IS __ " + endpoint);
        }
        return response.statusCode();
    }

    @Step("Send unauthorized DELETE requests on endpoint - {endpoint}")
    public static int unauthorizedDeletetRequests(String endpoint){
        getLogger().info("sending unauthorized DELETE requests on " + endpoint + " endpoint.");
        Response response = RestAssured.given()
                .delete(getBaseUrl() + endpoint);
        if (response.getStatusCode() != 401){
            getLogger().warn("Unauthorized request doesn't have 401 status code! ENDPOINT IS __ " + endpoint);
        }
        return response.statusCode();
    }

    @Step("Send unauthorized POST requests on endpoint - {endpoint}")
    public static int unauthorizedPostRequests(String endpoint){
        getLogger().info("sending unauthorized POST requests on " + endpoint + " endpoint.");
        Response response = RestAssured.given()
                .post(getBaseUrl() + endpoint);
        if (response.getStatusCode() != 401){
            getLogger().warn("Unauthorized request doesn't have 401 status code! ENDPOINT IS __ " + endpoint);
        }
        return response.statusCode();
    }
}
