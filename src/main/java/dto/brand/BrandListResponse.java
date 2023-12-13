package dto.brand;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class BrandListResponse {
    private List<BrandDto> content;

    @JsonCreator
    public BrandListResponse(@JsonProperty("content") List<BrandDto> content) {
        this.content = content;
    }
}
