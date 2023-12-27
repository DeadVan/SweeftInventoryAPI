package dto.attribute;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

import static utils.RandUtils.genRandNumb;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AttributeDto {
    private int attributeId;
    private String attributeName;
    private List<String> attributeValues;
}
