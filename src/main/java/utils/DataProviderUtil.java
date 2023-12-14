package utils;

import org.testng.annotations.DataProvider;

import static utils.DataReader.getEndPoint;

public class DataProviderUtil {

    @DataProvider(name = "endpoint")
    public static Object[][] getRegData() {
        return new Object[][] {
                {getEndPoint("brand_get"),getEndPoint("brand_put"),getEndPoint("brand_delete"),getEndPoint("brand_post")},
                {getEndPoint("brandList_get")},

                {getEndPoint("item_get"),getEndPoint("item_put"),getEndPoint("item_delete"),getEndPoint("item_post")},
                {getEndPoint("itemList_get")},

                {getEndPoint("category_get"),getEndPoint("category_put"),getEndPoint("category_delete"),getEndPoint("category_post")},
                {getEndPoint("categoriesList_get")},

                {getEndPoint("model_get"),getEndPoint("model_put"),getEndPoint("model_delete"),getEndPoint("model_post")},
                {getEndPoint("modelList_get")},

                {getEndPoint("stockItemDetailedId_get"),getEndPoint("stockItem_put"),getEndPoint("stockItemId_delete"),getEndPoint("stockItem_post")},
                {getEndPoint("stockItemList_get")},

                {getEndPoint("userView_get")},
                {getEndPoint("userViewDetailed_get")},
                {getEndPoint("userList_get")}
        };
    }
}
