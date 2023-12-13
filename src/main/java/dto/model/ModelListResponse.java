package dto.model;


import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class ModelListResponse {

    private List<ModelDto> content;

    @JsonCreator
    public ModelListResponse(@JsonProperty("content") List<ModelDto> content) {
        this.content = content;
    }
}
