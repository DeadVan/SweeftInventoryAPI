package utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import dto.CategoriesDto;
import dto.CategoriesListResponse;
import dto.UserDto;
import dto.UserListResponse;
import io.restassured.response.Response;
import java.util.List;

import static aquality.selenium.browser.AqualityServices.get;
import static aquality.selenium.browser.AqualityServices.getLogger;

public class ParseUtil {

    private static final ObjectMapper mapper = new ObjectMapper();
    public static UserDto parseUser(Response response) {
        getLogger().info("Parsing User");
        return response.as(UserDto.class);
    }


    public static List<UserDto> parseUsersList(Response response) {
        getLogger().info("parsing users list");
        try {
            UserListResponse userListResponse = mapper.readValue(response.getBody().asString(), UserListResponse.class);
            return userListResponse.getContent();
        }catch (JsonProcessingException e) {
            getLogger().error(e.getMessage());
            throw new RuntimeException("exception during parsing user list");
        }
    }

    public static List<CategoriesDto> parseCategoriesList(Response response){
        getLogger().info("parsing categories list");
        try {
            CategoriesListResponse categoriesListResponse = mapper.readValue(response.getBody().asString(),CategoriesListResponse.class);
            return categoriesListResponse.getContent();
        } catch (JsonProcessingException e) {
            getLogger().error(e.getMessage());
            throw new RuntimeException("exception during parsing categories");
        }
    }
}
