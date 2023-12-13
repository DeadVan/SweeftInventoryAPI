package dto.brand;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BrandDto {
    private int brandId;
    private String brandName;

    public static BrandDto postBrand = new BrandDto();


}
