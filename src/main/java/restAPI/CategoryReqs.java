package restAPI;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;

import java.io.File;

import static aquality.selenium.browser.AqualityServices.getLogger;
import static dto.category.CategoriesDto.postCategory;
import static dto.category.CategoryAddDto.categoryAddDto;
import static dto.LoginCrd.loginCrd;
import static utils.DataReader.*;

public class CategoryReqs {
    private static final ObjectMapper mapper = new ObjectMapper();

    public static Response getCategory(int categoryId){
        getLogger().info("sending get request for category with id - " + categoryId);
        return RestAssured.given()
                .header("Authorization","Bearer " + loginCrd.getAccessToken())
                .get(getBaseUrl() + getEndPoint("category_get") + categoryId);
    }
    public static Response getCategories(){
        getLogger().info("sending get request for categories list");
        return RestAssured.given()
                .header("Authorization","Bearer " + loginCrd.getAccessToken())
                .get(getBaseUrl() + getEndPoint("categoriesList_get"));
    }
    public static Response postCategory() {
        getLogger().info("sending post request to create category");
        try {
            Response response = RestAssured.given()
                    .header("Authorization","Bearer " + loginCrd.getAccessToken())
                    .contentType(ContentType.MULTIPART)
                    .multiPart("name",categoryAddDto.getName())
                    .multiPart("attributes",categoryAddDto.getAttributes())
                    .multiPart("icon",categoryAddDto.getIcon())
                    .post(getBaseUrl() + getEndPoint("category_post"));

            JsonNode jsonNode = mapper.readTree(response.body().asString());
            postCategory.setCategoryId(jsonNode.get("categoryId").asInt());
            getLogger().info("Created category  - " + postCategory);
            return response;
        } catch (JsonProcessingException e) {
            getLogger().error(e.getMessage());
            throw new RuntimeException("error while error while Processing Json");
        }
    }
    public static Response putCategory(){
        getLogger().info("sending put request to edit categorywith id -" +  postCategory.getCategoryId());
        categoryAddDto.setName("IphoniMagaria");
        categoryAddDto.setAttributes("maimuni");
        categoryAddDto.setIcon(new File(getTestData("icon2")));
        getLogger().info("sending put request to edit category, info --" +  postCategory);
        try {
            String jsonString = mapper.writeValueAsString(categoryAddDto);
            Response response =  RestAssured.given()
                    .header("Authorization","Bearer " + loginCrd.getAccessToken())
                    .contentType(ContentType.JSON)
                    .body(jsonString)
                    .put(getBaseUrl()+getEndPoint("")+ postCategory.getCategoryId());
            return response;
        } catch (JsonProcessingException e) {
            getLogger().error(e.getMessage());
            throw new RuntimeException("error while JsonProcessingException");
        }
    }
    public static Response deleteCategory(){
        getLogger().info("sending delete request for category with id - " + postCategory.getCategoryId());
        return RestAssured.given()
                .header("Authorization","Bearer " + loginCrd.getAccessToken())
                .delete(getBaseUrl() + getEndPoint("category_delete") + postCategory.getCategoryId());
    }
}
