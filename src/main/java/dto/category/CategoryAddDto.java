package dto.category;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.File;

import static utils.DataReader.getTestData;

@Data
@AllArgsConstructor
public class CategoryAddDto {
    private String name;
    private String attributes;
    private File icon;

    static String[] atribute = {getTestData("category_attributes")};
    static File iconn = new File(getTestData("icon"));

    public static CategoryAddDto categoryAddDto = new CategoryAddDto(getTestData("category_name"),"atribute",iconn);
}
