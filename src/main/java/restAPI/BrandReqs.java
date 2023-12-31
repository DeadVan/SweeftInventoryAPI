package restAPI;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.qameta.allure.Step;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import utils.StatusCode;

import static aquality.selenium.browser.AqualityServices.getLogger;
import static dto.brand.BrandDto.postBrand;
import static dto.LoginCrd.loginCrd;
import static dto.brand.BrandEditDto.brandEditDto;
import static utils.DataReader.getBaseUrl;
import static utils.DataReader.getEndPoint;
import static utils.RandUtils.generateString;

public class BrandReqs {
    private static final ObjectMapper mapper = new ObjectMapper();

    @Step("Get brands list")
    public static Response getBrands() {
        getLogger().info("sending GET request for brand list");
        Response response = RestAssured.given()
                .header("Authorization", "Bearer " + loginCrd.getAccessToken())
                .get(getBaseUrl() + getEndPoint("brandList_get"));
        if (response.getStatusCode() != StatusCode.OK.getCode()) {
            getLogger().error(response.getBody().asString());
        }
        return response;
    }

    @Step("Get brand with id - {brandId}")
    public static Response getBrand(int brandId) {
        getLogger().info("sending GET request for brand with id - " + brandId);
        Response response = RestAssured.given()
                .header("Authorization", "Bearer " + loginCrd.getAccessToken())
                .get(getBaseUrl() + getEndPoint("brand_get") + brandId);
        if (response.getStatusCode() != StatusCode.OK.getCode()) {
            getLogger().error(response.getBody().asString());
        }
        return response;
    }

    @Step("Create a brand")
    public static Response postBrand() {
        getLogger().info("sending POST request for brand");
        try {
            postBrand.setBrandName(generateString(7, 0, 0));
            String jsonString = mapper.writeValueAsString(postBrand);
            Response response = RestAssured.given()
                    .header("Authorization", "Bearer " + loginCrd.getAccessToken())
                    .contentType(ContentType.JSON)
                    .body(jsonString)
                    .post(getBaseUrl() + getEndPoint("brand_post"));
            JsonNode jsonNode = mapper.readTree(response.body().asString());
            postBrand.setBrandId(jsonNode.get("brandId").asInt());
            getLogger().info("Created Brand  - " + postBrand);
            if (response.getStatusCode() != StatusCode.CREATED.getCode()) {
                getLogger().error(response.getBody().asString());
            }
            return response;
        } catch (JsonProcessingException e) {
            getLogger().error(e.getMessage());
            throw new RuntimeException("error while processing json");
        }
    }

    @Step("Edit brand")
    public static Response putBrand() {
        getLogger().info("sending PUT request for brand");
        try {
            brandEditDto.setBrandName(generateString(7, 0, 0));
            String jsonString = mapper.writeValueAsString(brandEditDto);
            Response response = RestAssured.given()
                    .header("Authorization", "Bearer " + loginCrd.getAccessToken())
                    .contentType(ContentType.JSON)
                    .body(jsonString)
                    .put(getBaseUrl() + getEndPoint("brand_put") + postBrand.getBrandId());
            if (response.getStatusCode() != StatusCode.OK.getCode()) {
                getLogger().error(response.getBody().asString());
            }
            return response;
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    @Step("Delete brand with id - {brandId}")
    public static Response deleteBrand() {
        getLogger().info("sending DELETE request for brand with id - " + postBrand.getBrandId());
        Response response = RestAssured.given()
                .header("Authorization", "Bearer " + loginCrd.getAccessToken())
                .delete(getBaseUrl() + getEndPoint("brand_delete") + postBrand.getBrandId());
        if (response.getStatusCode() != StatusCode.OK.getCode()) {
            getLogger().error(response.getBody().asString());
        }
        return response;

    }
}
