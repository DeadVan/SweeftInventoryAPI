package restAPI;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;

import static aquality.selenium.browser.AqualityServices.getLogger;
import static dto.LoginCrd.loginCrd;
import static dto.brand.BrandDto.postBrand;
import static dto.model.ModelDto.postModel;
import static dto.model.ModelEditDto.modelEditDto;
import static utils.DataReader.getBaseUrl;
import static utils.DataReader.getEndPoint;
import static utils.ParseUtil.parseModelList;
import static utils.RandUtils.generateRandomNumber;
import static utils.RandUtils.generateString;
import static utils.ResponseUtils.*;

public class ModelReqs {

    private static final ObjectMapper mapper = new ObjectMapper();

    public static Response getModels(){
        getLogger().info("sending GET request for model list");
        return RestAssured.given()
                .header("Authorization","Bearer " + loginCrd.getAccessToken())
                .get(getBaseUrl() + getEndPoint("modelList_get"));
    }

    public static Response getModel(int modelId){
        getLogger().info("sending GET request for model with id - " + modelId);
        return RestAssured.given()
                .header("Authorization","Bearer " + loginCrd.getAccessToken())
                .get(getBaseUrl() + getEndPoint("model_get") + modelId);
    }
    public static Response postModel(){
        getLogger().info("sending POST request for model");
        try {
            postModel.setModelName(generateString(7,0,0));
            postModel.setBrandId(getRandomBrandId());
            postModel.setCategoryId(getRandomCategoryId());
            String jsonString = mapper.writeValueAsString(postModel);
            System.out.println(jsonString);
            Response response = RestAssured.given()
                    .header("Authorization","Bearer " + loginCrd.getAccessToken())
                    .contentType(ContentType.JSON)
                    .body(jsonString)
                    .post(getBaseUrl()+getEndPoint("model_post"));

            System.out.println(response.getBody().asString());
            JsonNode jsonNode = mapper.readTree(response.body().asString());
            postModel.setModelId(jsonNode.get("modelId").asInt());
            getLogger().info("Created model  - " + postModel);
            return response;
        }catch (JsonProcessingException e) {
            getLogger().error(e.getMessage());
            throw new RuntimeException("error while processing json");
        }
    }
    public static Response putModel(){
        getLogger().info("sending PUT request for model");
        try {
            modelEditDto.setModelName(generateString(7,0,0));
            modelEditDto.setBrandId(getRandomBrandId());
            modelEditDto.setCategoryId(getRandomCategoryId());
            String jsonString = mapper.writeValueAsString(modelEditDto);
            return RestAssured.given()
                    .header("Authorization","Bearer " + loginCrd.getAccessToken())
                    .contentType(ContentType.JSON)
                    .body(jsonString)
                    .put(getBaseUrl()+getEndPoint("model_put") + postModel.getModelId());
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
    public static Response deleteModel(){
        getLogger().info("sending DELETE request for model with id - " + postModel.getModelId());
        return RestAssured.given()
                .header("Authorization","Bearer " + loginCrd.getAccessToken())
                .delete(getBaseUrl()+getEndPoint("model_delete")+postModel.getModelId());

    }
}
