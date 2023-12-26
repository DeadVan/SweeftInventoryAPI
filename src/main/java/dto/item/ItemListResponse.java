package dto.item;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class ItemListResponse {

    private List<ItemDto> content;

    @JsonCreator
    public ItemListResponse(@JsonProperty("content") List<ItemDto> content) {
        this.content = content;
    }
}
