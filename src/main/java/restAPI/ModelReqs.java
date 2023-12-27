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
import static dto.LoginCrd.loginCrd;
import static dto.model.ModelDto.postModel;
import static dto.model.ModelEditDto.modelEditDto;
import static utils.DataReader.getBaseUrl;
import static utils.DataReader.getEndPoint;
import static utils.RandUtils.generateString;
import static utils.ResponseUtils.*;

public class ModelReqs {

    private static final ObjectMapper mapper = new ObjectMapper();

    @Step("Get models list")
    public static Response getModels() {
        getLogger().info("sending GET request for model list");
        Response response = RestAssured.given()
                .header("Authorization", "Bearer " + loginCrd.getAccessToken())
                .get(getBaseUrl() + getEndPoint("modelList_get"));
        if (response.getStatusCode() != StatusCode.OK.getCode()) {
            getLogger().error(response.getBody().asString());
        }
        return response;
    }


    @Step("Get model with id - {modelId}")
    public static Response getModel(int modelId) {
        getLogger().info("sending GET request for model with id - " + modelId);
        Response response = RestAssured.given()
                .header("Authorization", "Bearer " + loginCrd.getAccessToken())
                .get(getBaseUrl() + getEndPoint("model_get") + modelId);
        if (response.getStatusCode() != StatusCode.OK.getCode()) {
            getLogger().error(response.getBody().asString());
        }
        return response;
    }

    @Step("Create a model")
    public static Response postModel() {
        getLogger().info("sending POST request for model");
        try {
            postModel.setModelName(generateString(7, 0, 0));
            postModel.setBrandId(getRandomBrandId());
            postModel.setCategoryId(getRandomCategoryId());
            String jsonString = mapper.writeValueAsString(postModel);
            Response response = RestAssured.given()
                    .header("Authorization", "Bearer " + loginCrd.getAccessToken())
                    .contentType(ContentType.JSON)
                    .body(jsonString)
                    .post(getBaseUrl() + getEndPoint("model_post"));
            JsonNode jsonNode = mapper.readTree(response.body().asString());
            postModel.setModelId(jsonNode.get("modelId").asInt());
            if (response.getStatusCode() != StatusCode.CREATED.getCode()) {
                getLogger().error(response.getBody().asString());
            }
            getLogger().info("Created model  - " + postModel);
            return response;
        } catch (JsonProcessingException e) {
            getLogger().error(e.getMessage());
            throw new RuntimeException("error while processing json");
        }
    }

    @Step("Edit model")
    public static Response putModel() {
        getLogger().info("sending PUT request for model");
        try {
            modelEditDto.setModelName(generateString(7, 0, 0));
            modelEditDto.setBrandId(getRandomBrandId());
            modelEditDto.setCategoryId(getRandomCategoryId());
            String jsonString = mapper.writeValueAsString(modelEditDto);
            Response response = RestAssured.given()
                    .header("Authorization", "Bearer " + loginCrd.getAccessToken())
                    .contentType(ContentType.JSON)
                    .body(jsonString)
                    .put(getBaseUrl() + getEndPoint("model_put") + postModel.getModelId());
            if (response.getStatusCode() != StatusCode.OK.getCode()) {
                getLogger().error(response.getBody().asString());
            }
            return response;
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    @Step("Delete model with id")
    public static Response deleteModel() {
        getLogger().info("sending DELETE request for model with id - " + postModel.getModelId());
        Response response = RestAssured.given()
                .header("Authorization", "Bearer " + loginCrd.getAccessToken())
                .delete(getBaseUrl() + getEndPoint("model_delete") + postModel.getModelId());
        if (response.getStatusCode() != StatusCode.OK.getCode()) {
            getLogger().error(response.getBody().asString());
        }
        return response;
    }
}
