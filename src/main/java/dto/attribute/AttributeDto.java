package dto.attribute;


import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class AttributeDto {
    private int attributeId;
    private String attributeName;
    private List<String> attributeValues;


}
