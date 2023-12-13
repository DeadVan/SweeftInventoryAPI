package dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CategoriesDto {

    private String categoryName;
    private int categoryId;
    private String iconUrl;
    private String[] attributes;

    public static CategoriesDto postCategory = new CategoriesDto();


    @JsonCreator
    public CategoriesDto(@JsonProperty("categoryName")String categoryName,
                         @JsonProperty("categoryId")int categoryId,
                         @JsonProperty("iconUrl")String iconUrl,
                         @JsonProperty("attributes")String[] attributes) {
        this.categoryName = categoryName;
        this.categoryId = categoryId;
        this.iconUrl = iconUrl;
        this.attributes = attributes;
    }

    public CategoriesDto(int categoryId){
        this.categoryId = categoryId;
    }
}
