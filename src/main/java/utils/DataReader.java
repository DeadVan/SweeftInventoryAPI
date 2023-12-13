package utils;

import aquality.selenium.core.utilities.ISettingsFile;
import aquality.selenium.core.utilities.JsonSettingsFile;

public class DataReader {
    static ISettingsFile environment = new JsonSettingsFile("config.json");
    static ISettingsFile environment1 = new JsonSettingsFile("test_data.json");

    public static String getTestData(String value){
        return environment1.getValue("/" + value).toString();
    }

    public static String getBaseUrl() {
        return environment.getValue("/base_url").toString();
    }

    public static String getLoginPostEndpoint() {
        return environment.getValue("/login_post").toString();
    }
    public static String getUserRegisterEndpoint(){
        return environment.getValue("/user_register").toString();
    }
    public static String getUserDeleteEndpoint(){
        return environment.getValue("/user_delete").toString();
    }
    public static String getUserViewEndpoint() {
        return environment.getValue("/userView_get").toString();
    }

    public static String getUserListEndpoint() {
        return environment.getValue("/userList_get").toString();
    }

    public static String getUserViewDetailedEndpoint() {
        return environment.getValue("/userViewDetailed_get").toString();
    }

    public static String getStockItemAddEndpoint() {
        return environment.getValue("/stockItem_post").toString();
    }

    public static String getStockItemUpdateEndpoint() {
        return environment.getValue("/stockItem_put").toString();
    }
    public static String getItemRequestEndpoint() {
        return environment.getValue("/itemRequest_put").toString();
    }
    public static String getCategoriesEndpoint(){
        return environment.getValue("/categoriesList_get").toString();
    }

    public static String getCategoryByIdEndpoint(){
        return environment.getValue("/category_get").toString();
    }
    public static String postCategoryEndpoint(){
        return environment.getValue("/category_post").toString();
    }
    public static String putCategoryEndpoint(){
        return environment.getValue("/category_put").toString();
    }
    public static String deleteCategoryEndpoint(){
        return environment.getValue("/category_delete").toString();
    }

    public static String getStockItemListEndpoint() {
        return environment.getValue("/stockItemList_get").toString();
    }

    public static String getStockItemDetailedIdEndpoint() {
        return environment.getValue("/stockItemDetailedId_get").toString();
    }

    public static String getStockItemIdDeleteEndpoint() {
        return environment.getValue("/stockItemId_delete").toString();
    }
}
