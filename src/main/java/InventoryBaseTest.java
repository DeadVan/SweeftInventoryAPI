import io.qameta.allure.Allure;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeMethod;
import utils.StatusCode;

import java.nio.file.Files;
import java.nio.file.Paths;

import static aquality.selenium.browser.AqualityServices.getLogger;
import static dto.LoginCrd.loginCrd;
import static restAPI.UserReqs.logoutUser;
import static restAPI.UserReqs.postLogin;
import static utils.DataReader.getTestData;

public class InventoryBaseTest {
    public static InventoryTestSteps testSteps = new InventoryTestSteps();

    @BeforeMethod
    public void login(){
        Assert.assertEquals(postLogin().getStatusCode(), StatusCode.OK.getCode());
        Assert.assertFalse(loginCrd.getAccessToken().isEmpty(), "User doesn't have access token");
    }


    @AfterTest
    public void closeTest() {
        Assert.assertEquals(logoutUser().getStatusCode(),StatusCode.OK.getCode());
    }

    @AfterTest
    public void attachLogs() {
        try {
            String logContent = new String(Files.readAllBytes(Paths.get(getTestData("log_path"))));
            Allure.addAttachment("Test Logs", "text/plain", logContent);

        } catch (Exception e) {
            getLogger().info(e.getMessage());
        }
    }
}
