package utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import dto.attribute.AttributeDto;
import dto.attribute.AttributeListResposne;
import dto.brand.BrandDto;
import dto.brand.BrandListResponse;
import dto.category.CategoriesDto;
import dto.category.CategoriesListResponse;
import dto.item.ItemDto;
import dto.item.ItemListResponse;
import dto.model.ModelDto;
import dto.model.ModelListResponse;
import dto.user.UserDto;
import dto.user.UserListResponse;
import io.restassured.response.Response;

import java.util.List;

import static aquality.selenium.browser.AqualityServices.getLogger;
import static restAPI.AttributeReqs.getAttribute;
import static restAPI.BrandReqs.getBrands;
import static restAPI.CategoryReqs.getCategories;
import static restAPI.ItemReqs.getItems;
import static restAPI.ModelReqs.getModels;
import static restAPI.UserReqs.getUserList;

public class ParseUtil {

    private static final ObjectMapper mapper = new ObjectMapper();

    public static UserDto parseUser(Response response) {
        getLogger().info("Parsing User");
        return response.as(UserDto.class);
    }


    public static List<UserDto> parseUsersList() {
        getLogger().info("parsing users list");
        try {
            Response response = getUserList();
            UserListResponse userListResponse = mapper.readValue(response.getBody().asString(), UserListResponse.class);
            return userListResponse.getContent();
        } catch (JsonProcessingException e) {
            getLogger().error(e.getMessage());
            throw new RuntimeException("exception during parsing user list");
        }
    }

    public static List<CategoriesDto> parseCategoriesList() {
        getLogger().info("parsing categories list");
        try {
            Response response = getCategories();
            CategoriesListResponse categoriesListResponse = mapper.readValue(response.getBody().asString(), CategoriesListResponse.class);
            return categoriesListResponse.getContent();
        } catch (JsonProcessingException e) {
            getLogger().error(e.getMessage());
            throw new RuntimeException("exception during parsing categories");
        }
    }

    public static List<BrandDto> parseBrandList() {
        getLogger().info("parsing Brand list");
        try {
            Response response = getBrands();
            BrandListResponse brandListResponse = mapper.readValue(response.getBody().asString(), BrandListResponse.class);
            return brandListResponse.getContent();
        } catch (JsonProcessingException e) {
            getLogger().error(e.getMessage());
            throw new RuntimeException("exception during parsing brands");
        }
    }

    public static List<AttributeDto> parseAttribute(int categoryId) {
        getLogger().info("parsing attribute");
        try {
            Response response = getAttribute(categoryId);
            AttributeListResposne attributeListResposne = mapper.readValue(response.getBody().asString(), AttributeListResposne.class);
            return attributeListResposne.getContent();
        } catch (JsonProcessingException e) {
            getLogger().error(e.getMessage());
            throw new RuntimeException("exception during parsing attributes from category");
        }
    }

    public static List<ModelDto> parseModelList() {
        getLogger().info("parsing Model list");
        try {
            Response response = getModels();
            ModelListResponse modelListResponse = mapper.readValue(response.getBody().asString(), ModelListResponse.class);
            return modelListResponse.getContent();
        } catch (JsonProcessingException e) {
            getLogger().error(e.getMessage());
            throw new RuntimeException("exception during parsing models");
        }
    }

    public static List<ItemDto> parseItemList() {
        getLogger().info("parsing Item list");
        try {
            Response response = getItems();
            ItemListResponse itemListResponse = mapper.readValue(response.getBody().asString(), ItemListResponse.class);
            return itemListResponse.getContent();
        } catch (JsonProcessingException e) {
            getLogger().error(e.getMessage());
            throw new RuntimeException("exception during parsing Items");
        }
    }
}
