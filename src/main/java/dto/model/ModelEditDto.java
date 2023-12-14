package dto.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ModelEditDto {

    private String modelName;
    private int brandId;
    private int categoryId;

    public static ModelEditDto modelEditDto = new ModelEditDto();
}
