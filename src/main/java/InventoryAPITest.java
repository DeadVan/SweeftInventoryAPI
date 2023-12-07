import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import restAPI.UnauthorizedReqs;
import utils.StatusCode;

import static restAPI.UserController.*;
import static dto.LoginCrd.loginCrd;
import static utils.DataReader.*;
import static utils.DataReader.getStockItemUpdateEndpoint;
import static utils.ParseUtil.parseUser;
import static utils.ParseUtil.parseUsersList;
import static utils.ResponseUtils.checkIfGetUserListFilterWorks;

public class InventoryAPITest {

    @Test(testName = "Test authorization and user controller reqs")
    public void testLogin() {
        Assert.assertEquals(postLogin().getStatusCode(), StatusCode.OK.getCode(), "Expected status code to be 200(postLogin)");
        Assert.assertFalse(loginCrd.getAccessToken().isEmpty(), "User doesn't have access token");
        Assert.assertEquals(getUserView().getStatusCode(), StatusCode.OK.getCode(), "Expected status code to be 200(getUserView)");
        Assert.assertEquals(parseUser(getUserView()).getEmail(), getTestData("user_email"), "user email and email from test_data is not equal");
        Assert.assertEquals(getUserList().statusCode(), StatusCode.OK.getCode(), "Expected status code to be 200(getUserList)");
        Assert.assertTrue(checkIfGetUserListFilterWorks(parseUsersList(getUserList())),"filter is working incorrectly");
    }

    @Test(testName = "Test unauthorized requests", dataProvider = "endpoint")
    public void testUnauthorizedRequests(String getEndpoint,String putEndpoint){
        Assert.assertEquals(UnauthorizedReqs.unauthorizedGetRequests(getEndpoint),StatusCode.UNAUTHORIZED.getCode(),"request should be UNAUTHORIZED! with 401 status code");
        Assert.assertEquals(UnauthorizedReqs.unauthorizedPuttRequests(putEndpoint),StatusCode.UNAUTHORIZED.getCode(),"request should be UNAUTHORIZED!(putStockItem)");
    }

    @DataProvider(name = "endpoint")
    public static Object[][] getRegData() {
        return new Object[][] {
                {getUserViewEndpoint(),getStockItemUpdateEndpoint()},
                {getUserViewDetailedEndpoint(),getStockItemAddEndpoint()},
                {getUserListEndpoint(),getItemRequestEndpoint()}
        };
    }
}
