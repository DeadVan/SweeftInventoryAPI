package dto.category;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.File;
import java.util.Arrays;
import java.util.List;

import static utils.DataReader.getTestData;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CategoryEditDto {
    private String name;
    private List<String> attributes;

    public static CategoryEditDto categoryEditDto = new CategoryEditDto();
}
