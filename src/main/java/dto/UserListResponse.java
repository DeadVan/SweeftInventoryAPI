package dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class UserListResponse {

    private List<UserDto> content;

    @JsonCreator
    public UserListResponse(@JsonProperty("content") List<UserDto> content) {
        this.content = content;
    }
}
