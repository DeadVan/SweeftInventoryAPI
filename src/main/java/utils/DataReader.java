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
    public static String getEndPoint(String value){
        return environment.getValue("/"+ value).toString();
    }
}
