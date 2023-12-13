package dto;


import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class CategoriesListResponse {

    private List<CategoriesDto> content;

    @JsonCreator
    public CategoriesListResponse(@JsonProperty("content") List<CategoriesDto> content) {
        this.content = content;
    }
}
