package dto.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ModelDto {
    private int modelId;
    private String modelName;
    private int brandId;
    private String brandName;
    private int categoryId;
    private String categoryName;

    public static ModelDto postModel = new ModelDto();


    public ModelDto(String modelName, int brandId, int categoryId) {
        this.modelName = modelName;
        this.brandId = brandId;
        this.categoryId = categoryId;
    }
}
