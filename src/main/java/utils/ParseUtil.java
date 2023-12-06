package utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import dto.UserDto;
import dto.UserListResponse;
import io.restassured.response.Response;
import java.util.List;

import static aquality.selenium.browser.AqualityServices.getLogger;

public class ParseUtil {
    public static UserDto parseUser(Response response) {
        getLogger().info("Parsing User");
        return response.as(UserDto.class);
    }


    public static List<UserDto> parseUsersList(Response response) {
        getLogger().info("parsing users list");
        try {
            ObjectMapper mapper = new ObjectMapper();
            UserListResponse userListResponse = mapper.readValue(response.getBody().asString(), UserListResponse.class);
            return userListResponse.getContent();
        }catch (JsonProcessingException e) {
            getLogger().error(e.getMessage());
            throw new RuntimeException("exception during parsing user list");
        }
    }
}
