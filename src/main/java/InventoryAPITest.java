import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import restAPI.UnauthorizedReqs;
import utils.StatusCode;

import static restAPI.BrandReqs.*;
import static restAPI.ModelReqs.*;
import static restAPI.UserReqs.*;
import static dto.LoginCrd.loginCrd;
import static restAPI.CategoryReqs.*;
import static utils.DataReader.*;
import static utils.ParseUtil.*;
import static utils.ResponseUtils.*;

public class InventoryAPITest {

    @Test(testName = "Test User controller,Category controller requests")
    public void inventoryTest() {
        Assert.assertEquals(postLogin().getStatusCode(), StatusCode.OK.getCode());
        Assert.assertFalse(loginCrd.getAccessToken().isEmpty(), "User doesn't have access token");
        Assert.assertEquals(getUserView().getStatusCode(), StatusCode.OK.getCode());
        Assert.assertEquals(parseUser(getUserView()).getEmail(), getTestData("user_email"), "user email and email from test_data is not equal");
        Assert.assertEquals(getUserList().getStatusCode(), StatusCode.OK.getCode());
        Assert.assertTrue(checkIfGetUserListFilterWorks(parseUsersList()),"filter is working incorrectly");

        Assert.assertEquals(registerUser().getStatusCode(),StatusCode.CREATED.getCode());
        Assert.assertEquals(deleteUser().getStatusCode(),StatusCode.OK.getCode());

        Assert.assertEquals(getCategories().getStatusCode(),StatusCode.PARTIAL_RESPONSE.getCode());
        Assert.assertEquals(getRandomCategory(parseCategoriesList()),StatusCode.OK.getCode());
        Assert.assertEquals(postCategory().getStatusCode(),StatusCode.CREATED.getCode());
        Assert.assertEquals(deleteCategory().getStatusCode(),StatusCode.OK.getCode());

        Assert.assertEquals(getBrands().getStatusCode(),StatusCode.OK.getCode());
        Assert.assertEquals(getRandomBrand(parseBrandList()),StatusCode.OK.getCode());
        Assert.assertEquals(postBrand().getStatusCode(),StatusCode.CREATED.getCode());
        Assert.assertEquals(deleteBrand().getStatusCode(),StatusCode.OK.getCode());

        Assert.assertEquals(getModels().getStatusCode(),StatusCode.OK.getCode());
        Assert.assertEquals(getRandomModel(parseModelList()),StatusCode.OK.getCode());
        Assert.assertEquals(postModel().getStatusCode(),StatusCode.CREATED.getCode());
        Assert.assertEquals(deleteModel().getStatusCode(),StatusCode.OK.getCode());
    }

    @Test(testName = "Test unauthorized requests", dataProvider = "endpoint")
    public void testUnauthorizedRequests(String getEndpoint,String putEndpoint){
        Assert.assertEquals(UnauthorizedReqs.unauthorizedGetRequests(getEndpoint),StatusCode.UNAUTHORIZED.getCode(),"request should be UNAUTHORIZED! with 401 status code");
        Assert.assertEquals(UnauthorizedReqs.unauthorizedPuttRequests(putEndpoint),StatusCode.UNAUTHORIZED.getCode(),"request should be UNAUTHORIZED! with 401 status code (putStockItem)");
    }

    @DataProvider(name = "endpoint")
    public static Object[][] getRegData() {
        return new Object[][] {
                {getEndPoint("userView_get"),getEndPoint("stockItem_put")},
                {getEndPoint("userViewDetailed_get"),getEndPoint("stockItem_post")},
                {getEndPoint("userList_get"),getEndPoint("itemRequest_put")}
        };
    }
}
