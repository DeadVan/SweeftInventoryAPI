package dto.brand;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BrandEditDto {

    private String brandName;

    public static BrandEditDto brandEditDto = new BrandEditDto();

}
