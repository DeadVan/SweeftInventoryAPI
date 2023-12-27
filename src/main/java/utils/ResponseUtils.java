package utils;

import dto.brand.BrandDto;
import dto.category.CategoriesDto;
import dto.item.ItemDto;
import dto.model.ModelDto;
import dto.user.UserDto;

import java.util.ArrayList;
import java.util.List;

import static restAPI.BrandReqs.getBrand;
import static restAPI.CategoryReqs.getCategory;
import static restAPI.ItemReqs.getItem;
import static restAPI.ModelReqs.getModel;
import static utils.DataReader.getTestData;
import static utils.ParseUtil.*;
import static utils.RandUtils.genRandNumb;

public class ResponseUtils {

    public static boolean checkIfGetUserListFilterWorks(List<UserDto> userDtos) {
        for (UserDto info : userDtos) {
            if (info.getUserRole().equals(getTestData("filter_role")) && info.getDbStatus().equals(getTestData("filter_status"))) {
                return true;
            }
        }
        return false;
    }

    public static boolean checkIfGetCategoriesListWorks(List<CategoriesDto> categoriesDtos) {
        return categoriesDtos.size() > 1;
    }

    public static int getRandomBrand(List<BrandDto> brandDtos) {
        int size = brandDtos.size() - 1;
        return getBrand(brandDtos.get(genRandNumb(size)).getBrandId()).getStatusCode();
    }

    public static int getRandomItem(List<ItemDto> itemDtos) {
        int size = itemDtos.size() - 1;
        return getItem(itemDtos.get(genRandNumb(size)).getItemId()).getStatusCode();
    }

    public static int getRandomCategory(List<CategoriesDto> categoriesDtos) {
        int size = categoriesDtos.size() - 1;
        return getCategory(categoriesDtos.get(genRandNumb(size)).getCategoryId()).getStatusCode();
    }

    public static int getRandomModel(List<ModelDto> modelDtos) {
        int size = modelDtos.size() - 1;
        return getModel(modelDtos.get(genRandNumb(size)).getModelId()).getStatusCode();
    }

    public static int getRandomBrandId() {
        List<Integer> brandIds = new ArrayList<>();
        for (BrandDto brandDto : parseBrandList()) {
            brandIds.add(brandDto.getBrandId());
        }
        int size = brandIds.size() - 1;
        return brandIds.get(genRandNumb(size));
    }

    public static int getRandomCategoryId() {
        List<Integer> categoryIds = new ArrayList<>();
        for (CategoriesDto categoriesDto : parseCategoriesList()) {
            categoryIds.add(categoriesDto.getCategoryId());
        }
        int size = categoryIds.size() - 1;
        return categoryIds.get(genRandNumb(size));
    }

}
