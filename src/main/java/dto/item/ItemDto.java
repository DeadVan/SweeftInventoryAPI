package dto.item;

import dto.attribute.AttributeDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.openqa.selenium.devtools.v85.domstorage.model.Item;

import java.util.List;

import static utils.RandUtils.generateString;

@Data
@NoArgsConstructor
public class ItemDto {

    private int itemId;
    private String itemName;
    private int categoryId;
    private int modelId;
    private String categoryName;
    private String brandName;
    private String modelName;
    private int quantityInStock;
    private List<AttributeDto> attributesWithValues;
    private List<String> serialIds;

    public ItemDto(String itemName, int modelId, List<AttributeDto> attributesWithValues, List<String> serialIds) {
        this.itemName = itemName;
        this.modelId = modelId;
        this.attributesWithValues = attributesWithValues;
        this.serialIds = serialIds;
    }

}
