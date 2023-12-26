package dto.attribute;


import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class AttributeListResposne {

    private List<AttributeDto> content;

    @JsonCreator
    public AttributeListResposne(@JsonProperty("content") List<AttributeDto> content) {
        this.content = content;
    }
}
