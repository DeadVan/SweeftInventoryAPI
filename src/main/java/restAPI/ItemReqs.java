package restAPI;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import dto.attribute.AttributeDto;
import dto.item.ItemDto;
import dto.model.ModelDto;
import io.qameta.allure.Step;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import utils.StatusCode;

import java.util.Arrays;
import java.util.List;

import static aquality.selenium.browser.AqualityServices.getLogger;
import static dto.LoginCrd.loginCrd;
import static utils.DataReader.getBaseUrl;
import static utils.DataReader.getEndPoint;
import static utils.ParseUtil.parseAttribute;
import static utils.ParseUtil.parseModelList;
import static utils.RandUtils.genRandNumb;
import static utils.RandUtils.generateString;

public class ItemReqs {

    private static final ObjectMapper mapper = new ObjectMapper();
    private static ItemDto itemDto;


    @Step("Get item with id - {itemId}")
    public static Response getItem(int itemId){
        getLogger().info("sending GET request for item with id - " + itemId);
        Response response = RestAssured.given()
                .header("Authorization","Bearer " + loginCrd.getAccessToken())
                .get(getBaseUrl() + getEndPoint("item_get") + itemId);
        System.out.println(response.getBody().asString());
        if (response.getStatusCode() != StatusCode.OK.getCode()){
            getLogger().error(response.getBody().asString());
        }
        return response;
    }

    @Step("Get item list")
    public static Response getItems(){
        getLogger().info("sending GET request for item list");
        Response response = RestAssured.given()
                .header("Authorization","Bearer " + loginCrd.getAccessToken())
                .get(getBaseUrl() + getEndPoint("itemList_get"));
        System.out.println(response.getBody().asString());
        if (response.getStatusCode() != StatusCode.OK.getCode()){
            getLogger().error(response.getBody().asString());
        }
        return response;
    }

    @Step("Create a item")
    public static Response postItem(){
        getLogger().info("sending POST request for item");
        try {
            List<ModelDto> models = parseModelList();
            ModelDto model = models.get(genRandNumb(models.size()-1));
            List<AttributeDto> attributes = parseAttribute(model.getCategoryId());
            List<String> serialIds = List.of(generateString(10, 0, 0));

            itemDto = new ItemDto(generateString(15,0,0),model.getModelId(),attributes,serialIds);
            System.out.println(itemDto);
            String jsonString = mapper.writeValueAsString(itemDto);
            Response response = RestAssured.given()
                    .header("Authorization","Bearer " + loginCrd.getAccessToken())
                    .contentType(ContentType.JSON)
                    .body(jsonString)
                    .post(getBaseUrl()+getEndPoint("item_post"));

            System.out.println(response.getBody().asString());
            JsonNode jsonNode = mapper.readTree(response.body().asString());
            itemDto.setItemId(jsonNode.get("itemId").asInt());
            if (response.getStatusCode() != StatusCode.CREATED.getCode()){
                getLogger().error(response.getBody().asString());
            }
            getLogger().info("Created item  - " + itemDto);
            return response;
        }catch (JsonProcessingException e) {
            getLogger().error(e.getMessage());
            throw new RuntimeException("error while processing json");
        }
    }

    @Step("Delete item with id")
    public static Response deleteItem() {
        getLogger().info("sending DELETE request for item with id - " + itemDto.getItemId());
        Response response = RestAssured.given()
                .header("Authorization", "Bearer " + loginCrd.getAccessToken())
                .delete(getBaseUrl() + getEndPoint("item_delete") + itemDto.getModelId());
        System.out.println(response.getBody().asString());
        if (response.getStatusCode() != StatusCode.OK.getCode()){
            getLogger().error(response.getBody().asString());
        }
        return response;
    }
}
