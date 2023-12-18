package restAPI;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import utils.StatusCode;

import java.io.File;
import java.util.Arrays;

import static aquality.selenium.browser.AqualityServices.getLogger;
import static dto.category.CategoriesDto.postCategory;
import static dto.category.CategoryAddDto.categoryAddDto;
import static dto.LoginCrd.loginCrd;
import static dto.category.CategoryEditDto.categoryEditDto;
import static utils.DataReader.*;

public class CategoryReqs {
    private static final ObjectMapper mapper = new ObjectMapper();

    public static Response getCategory(int categoryId) {
        getLogger().info("sending GET request for category with id - " + categoryId);
        Response response = RestAssured.given()
                .header("Authorization", "Bearer " + loginCrd.getAccessToken())
                .get(getBaseUrl() + getEndPoint("category_get") + categoryId);
        if (response.getStatusCode() != StatusCode.OK.getCode()){
            getLogger().error(response.getBody().asString());
        }
        return response;
    }

    public static Response getCategories() {
        getLogger().info("sending GET request for categories list");
        Response response = RestAssured.given()
                .header("Authorization", "Bearer " + loginCrd.getAccessToken())
                .get(getBaseUrl() + getEndPoint("categoriesList_get"));
        if (response.getStatusCode() != StatusCode.PARTIAL_RESPONSE.getCode()){
            getLogger().error(response.getBody().asString() + "||||" + response.getStatusCode());
        }
        return response;
    }

    public static Response postCategory() {
        getLogger().info("sending POST request to create category");
        try {
            Response response = RestAssured.given()
                    .header("Authorization", "Bearer " + loginCrd.getAccessToken())
                    .contentType(ContentType.MULTIPART)
                    .multiPart("name", categoryAddDto.getName())
                    .multiPart("attributes", categoryAddDto.getAttributes())
                    .multiPart("icon", categoryAddDto.getIcon())
                    .post(getBaseUrl() + getEndPoint("category_post"));

            JsonNode jsonNode = mapper.readTree(response.body().asString());
            postCategory.setCategoryId(jsonNode.get("categoryId").asInt());
            if (response.getStatusCode() != StatusCode.CREATED.getCode()){
                getLogger().error(response.getBody().asString());
            }
            getLogger().info("Created category  - " + postCategory);
            return response;
        } catch (JsonProcessingException e) {
            getLogger().error(e.getMessage());
            throw new RuntimeException("error while error while Processing Json");
        }
    }

    public static Response putCategory() {
        getLogger().info("sending PUT request to edit category with id -" + postCategory.getCategoryId());

        categoryEditDto.setName("magatiTLELELDSD");
        categoryEditDto.setAttributes(Arrays.asList(categoryAddDto.getAttributes()));
        getLogger().info("Category" + categoryEditDto);
        try {
            String jsonString = mapper.writeValueAsString(categoryEditDto);

            Response response = RestAssured.given()
                    .header("Authorization", "Bearer " + loginCrd.getAccessToken())
                    .contentType(ContentType.JSON)
                    .body(jsonString)
                    .put(getBaseUrl() + getEndPoint("category_put") + postCategory.getCategoryId());
            if (response.getStatusCode() != StatusCode.OK.getCode()){
                getLogger().error(response.getBody().asString());
            }
            return response;
        } catch (JsonProcessingException e) {
            getLogger().error(e.getMessage());
            throw new RuntimeException("error while JsonProcessingException");
        }
    }

    public static Response putCategoryIcon() {
        getLogger().info("sending PUT request to edit category ICON with id -" + postCategory.getCategoryId());
        Response response =  RestAssured.given()
                .header("Authorization", "Bearer " + loginCrd.getAccessToken())
                .contentType(ContentType.MULTIPART)
                .multiPart("icon",new File("src/main/resources/screenshot2.png"))
                .queryParam("categoryId",postCategory.getCategoryId())
                .put(getBaseUrl() + getEndPoint("categoryIcon_put"));
        if (response.getStatusCode() != StatusCode.OK.getCode()){
            getLogger().error(response.getBody().asString());
        }
        return response;
    }

    public static Response deleteCategory() {
        getLogger().info("sending DELETE request for category with id - " + postCategory.getCategoryId());
        Response response = RestAssured.given()
                .header("Authorization", "Bearer " + loginCrd.getAccessToken())
                .delete(getBaseUrl() + getEndPoint("category_delete") + postCategory.getCategoryId());
        if (response.getStatusCode() != StatusCode.OK.getCode()){
            getLogger().error(response.getBody().asString());
        }
        return response;
    }
}
