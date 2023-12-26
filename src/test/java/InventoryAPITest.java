import io.qameta.allure.*;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.Test;
import restAPI.UnauthorizedReqs;
import utils.DataProviderUtil;
import utils.StatusCode;

import java.nio.file.Files;
import java.nio.file.Paths;

import static aquality.selenium.browser.AqualityServices.getLogger;
import static restAPI.BrandReqs.*;
import static restAPI.ItemReqs.*;
import static restAPI.ModelReqs.*;
import static restAPI.UserReqs.*;
import static dto.LoginCrd.loginCrd;
import static restAPI.CategoryReqs.*;
import static utils.DataReader.*;
import static utils.ParseUtil.*;
import static utils.ResponseUtils.*;

@Epic("Inventory API")
@Feature("Inventory API Tests")
public class InventoryAPITest {
    @Test(testName = "Test User controller,Category controller requests")
    @Severity(SeverityLevel.CRITICAL)
    @Description("Test various requests related to admin based controllers")
    public void inventoryTest() {
        Assert.assertEquals(postLogin().getStatusCode(), StatusCode.OK.getCode());
        Assert.assertFalse(loginCrd.getAccessToken().isEmpty(), "User doesn't have access token");

        Assert.assertEquals(changePassword().getStatusCode(),StatusCode.OK.getCode());
        Assert.assertEquals(resetPassword().getStatusCode(),StatusCode.OK.getCode());
//        Assert.assertEquals(restoreForgottenPass().getStatusCode(),StatusCode.OK.getCode());

        Assert.assertEquals(getUserView().getStatusCode(), StatusCode.OK.getCode());
        Assert.assertEquals(parseUser(getUserView()).getEmail(), getTestData("user_email"), "user email and email from test_data is not equal");
        Assert.assertEquals(getUserList().getStatusCode(), StatusCode.OK.getCode());
        Assert.assertTrue(checkIfGetUserListFilterWorks(parseUsersList()),"filter is working incorrectly");

        Assert.assertEquals(registerUser().getStatusCode(),StatusCode.CREATED.getCode());
        Assert.assertEquals(deleteUser().getStatusCode(),StatusCode.OK.getCode());

        Assert.assertEquals(getCategories().getStatusCode(),StatusCode.PARTIAL_RESPONSE.getCode());
        Assert.assertEquals(getRandomCategory(parseCategoriesList()),StatusCode.OK.getCode());
        Assert.assertEquals(postCategory().getStatusCode(),StatusCode.CREATED.getCode());
        Assert.assertEquals(putCategory().getStatusCode(),StatusCode.OK.getCode());
        Assert.assertEquals(putCategoryIcon().getStatusCode(),StatusCode.OK.getCode());
        Assert.assertEquals(deleteCategory().getStatusCode(),StatusCode.OK.getCode());

        Assert.assertEquals(getBrands().getStatusCode(),StatusCode.OK.getCode());
        Assert.assertEquals(getRandomBrand(parseBrandList()),StatusCode.OK.getCode());
        Assert.assertEquals(postBrand().getStatusCode(),StatusCode.CREATED.getCode());
        Assert.assertEquals(putBrand().getStatusCode(),StatusCode.OK.getCode());
        Assert.assertEquals(deleteBrand().getStatusCode(),StatusCode.OK.getCode());

        Assert.assertEquals(getModels().getStatusCode(),StatusCode.OK.getCode());
        Assert.assertEquals(getRandomModel(parseModelList()),StatusCode.OK.getCode());
        Assert.assertEquals(postModel().getStatusCode(),StatusCode.CREATED.getCode());
        Assert.assertEquals(putModel().getStatusCode(),StatusCode.OK.getCode());
        Assert.assertEquals(deleteModel().getStatusCode(),StatusCode.OK.getCode());


        Assert.assertEquals(getItems().getStatusCode(),StatusCode.OK.getCode());
        Assert.assertEquals(getRandomItem(parseItemList()),StatusCode.OK.getCode());
//        Assert.assertEquals(postItem().getStatusCode(),StatusCode.CREATED.getCode());
//        Assert.assertEquals(deleteItem().getStatusCode(),StatusCode.OK.getCode());


        Assert.assertEquals(logoutUser().getStatusCode(),StatusCode.OK.getCode());
    }

    @Test(testName = "Test unauthorized requests", dataProviderClass = DataProviderUtil.class, dataProvider = "endpoint")
    @Severity(SeverityLevel.NORMAL)
    @Description("Test unauthorized requests using different HTTP methods")
    public void testUnauthorizedRequests(String... getEndpoint){
        Assert.assertEquals(UnauthorizedReqs.unauthorizedGetRequests(getEndpoint[0]),StatusCode.UNAUTHORIZED.getCode(),"request should be UNAUTHORIZED! with 401 status code(Get)");
        if (getEndpoint.length > 1){
            Assert.assertEquals(UnauthorizedReqs.unauthorizedPuttRequests(getEndpoint[1]),StatusCode.UNAUTHORIZED.getCode(),"request should be UNAUTHORIZED! with 401 status code (PUT)");
        }
        if (getEndpoint.length > 2){
            Assert.assertEquals(UnauthorizedReqs.unauthorizedDeletetRequests(getEndpoint[2]),StatusCode.UNAUTHORIZED.getCode(),"request should be UNAUTHORIZED! with 401 status code (DELETE)");
        }
        if (getEndpoint.length > 3){
            Assert.assertEquals(UnauthorizedReqs.unauthorizedPostRequests(getEndpoint[3]),StatusCode.UNAUTHORIZED.getCode(),"request should be UNAUTHORIZED! with 401 status code (POST)");
        }
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
