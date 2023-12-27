package restAPI;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import dto.attribute.AttributeDto;
import io.qameta.allure.Step;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import utils.StatusCode;
import java.util.List;

import static aquality.selenium.browser.AqualityServices.getLogger;
import static dto.LoginCrd.loginCrd;
import static dto.item.ItemDto.itemDto;
import static utils.DataReader.getBaseUrl;
import static utils.DataReader.getEndPoint;
import static utils.ParseUtil.parseAttribute;
import static utils.ParseUtil.parseModelList;
import static utils.RandUtils.genRandNumb;
import static utils.RandUtils.generateString;

public class ItemReqs {

    private static final ObjectMapper mapper = new ObjectMapper();
    public static List<AttributeDto> attributes;


    @Step("Get item with id - {itemId}")
    public static Response getItem(int itemId) {
        getLogger().info("sending GET request for item with id - " + itemId);
        Response response = RestAssured.given()
                .header("Authorization", "Bearer " + loginCrd.getAccessToken())
                .get(getBaseUrl() + getEndPoint("item_get") + itemId);
        if (response.getStatusCode() != StatusCode.OK.getCode()) {
            getLogger().error(response.getBody().asString());
        }
        return response;
    }

    @Step("Get item list")
    public static Response getItems() {
        getLogger().info("sending GET request for item list");
        Response response = RestAssured.given()
                .header("Authorization", "Bearer " + loginCrd.getAccessToken())
                .get(getBaseUrl() + getEndPoint("itemList_get"));
        if (response.getStatusCode() != StatusCode.OK.getCode()) {
            getLogger().error(response.getBody().asString());
        }
        return response;
    }

    @Step("Create a item")
    public static Response postItem() {
        getLogger().info("sending POST request for item");
        try {
//            List<ModelDto> models = parseModelList();
//            ModelDto model = models.get(genRandNumb(models.size()-1));
//            attributes = parseAttribute(model.getCategoryId());
//            List<String> serialIds = List.of(generateString(10, 0, 0));
//
//            itemDto = new ItemDto(generateString(15,0,0),model.getModelId(),attributes,serialIds);
//            String jsonString = mapper.writeValueAsString(itemDto);
            String jsonString = "{\n" +
                    "  \"itemName\": \"NewTestAutomation\",\n" +
                    "  \"modelId\": 858,\n" +
                    "    \"attributesWithValues\": [\n" +
                    "      {\n" +
                    "        \"attributeId\": 2,\n" +
                    "        \"attributeValue\": \"8gb\"\n" +
                    "      }\n" +
                    "    ],\n" +
                    "  \"serialIds\": [\n" +
                    "    \"" + generateString(14, 0, 0) + "\"\n" +
                    "  ]\n" +
                    "}";
            Response response = RestAssured.given()
                    .header("Authorization", "Bearer " + loginCrd.getAccessToken())
                    .contentType(ContentType.JSON)
                    .body(jsonString)
                    .post(getBaseUrl() + getEndPoint("item_post"));

            JsonNode jsonNode = mapper.readTree(response.body().asString());
            itemDto.setItemId(jsonNode.get("itemId").asInt());
            if (response.getStatusCode() != StatusCode.CREATED.getCode()) {
                getLogger().error(response.getBody().asString());
                return response;
            }
            getLogger().info("Created item  - " + itemDto);
            return response;
        } catch (JsonProcessingException e) {
            getLogger().error(e.getMessage());
            throw new RuntimeException("error while processing json");
        }
    }

    @Step("Update a item")
    public static Response putItem() {
        getLogger().info("sending PUT request for item");
        try {
            itemDto.setSerialIds(List.of(generateString(14, 0, 0)));
            String jsonString = mapper.writeValueAsString(itemDto);
            Response response = RestAssured.given()
                    .header("Authorization", "Bearer " + loginCrd.getAccessToken())
                    .contentType(ContentType.JSON)
                    .body(jsonString)
                    .put(getBaseUrl() + getEndPoint("item_put") + itemDto.getItemId());
            if (response.getStatusCode() != StatusCode.OK.getCode()) {
                getLogger().error(response.getBody().asString());
                return response;
            }
            getLogger().info("updated item  - " + itemDto);
            return response;
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    @Step("Delete item with id")
    public static Response deleteItem() {
        getLogger().info("sending DELETE request for item with id - " + itemDto.getItemId());
        Response response = RestAssured.given()
                .header("Authorization", "Bearer " + loginCrd.getAccessToken())
                .delete(getBaseUrl() + getEndPoint("item_delete") + itemDto.getItemId());
        if (response.getStatusCode() != StatusCode.OK.getCode()) {
            getLogger().error(response.getBody().asString());
        }
        return response;
    }
}
