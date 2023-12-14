package restAPI;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;

import static aquality.selenium.browser.AqualityServices.getLogger;
import static dto.brand.BrandDto.postBrand;
import static dto.LoginCrd.loginCrd;
import static dto.brand.BrandEditDto.brandEditDto;
import static utils.DataReader.getBaseUrl;
import static utils.DataReader.getEndPoint;
import static utils.RandUtils.generateString;

public class BrandReqs {
    private static final ObjectMapper mapper = new ObjectMapper();

    public static Response getBrands(){
        getLogger().info("sending GET request for brand list");
        return RestAssured.given()
                .header("Authorization","Bearer " + loginCrd.getAccessToken())
                .get(getBaseUrl() + getEndPoint("brandList_get"));
    }

    public static Response getBrand(int brandId){
        getLogger().info("sending GET request for brand with id - " + brandId);
        return RestAssured.given()
                .header("Authorization","Bearer " + loginCrd.getAccessToken())
                .get(getBaseUrl() + getEndPoint("brand_get") + brandId);
    }
    public static Response postBrand(){
        getLogger().info("sending POST request for brand");
        try {
            postBrand.setBrandName(generateString(7,0,0));
            String jsonString = mapper.writeValueAsString(postBrand);
            Response response = RestAssured.given()
                    .header("Authorization","Bearer " + loginCrd.getAccessToken())
                    .contentType(ContentType.JSON)
                    .body(jsonString)
                    .post(getBaseUrl()+getEndPoint("brand_post"));
            JsonNode jsonNode = mapper.readTree(response.body().asString());
            postBrand.setBrandId(jsonNode.get("brandId").asInt());
            getLogger().info("Created Brand  - " + postBrand);
            return response;
        }catch (JsonProcessingException e) {
            getLogger().error(e.getMessage());
            throw new RuntimeException("error while processing json");
        }
    }
    public static Response putBrand(){
        getLogger().info("sending PUT request for brand");
        try {
            brandEditDto.setBrandName(generateString(7,0,0));
            String jsonString = mapper.writeValueAsString(brandEditDto);
            return   RestAssured.given()
                    .header("Authorization","Bearer " + loginCrd.getAccessToken())
                    .contentType(ContentType.JSON)
                    .body(jsonString)
                    .put(getBaseUrl()+getEndPoint("brand_put") + postBrand.getBrandId());
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
    public static Response deleteBrand(){
        getLogger().info("sending DELETE request for brand with id - " + postBrand.getBrandId());
        return RestAssured.given()
                .header("Authorization","Bearer " + loginCrd.getAccessToken())
                .delete(getBaseUrl()+getEndPoint("brand_delete")+postBrand.getBrandId());

    }
}
