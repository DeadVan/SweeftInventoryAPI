import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import restAPI.UnauthorizedReqs;
import utils.StatusCode;

import static restAPI.UserController.*;
import static dto.LoginCrd.loginCrd;
import static restAPI.CategoryController.*;
import static utils.DataReader.*;
import static utils.DataReader.getStockItemUpdateEndpoint;
import static utils.ParseUtil.*;
import static utils.ResponseUtils.checkIfGetUserListFilterWorks;

public class InventoryAPITest {

    @Test(testName = "Test User controller,Category controller requests")
    public void inventoryTest() {
        Assert.assertEquals(postLogin().getStatusCode(), StatusCode.OK.getCode(), "Expected status code to be 200(post Login)");
        Assert.assertFalse(loginCrd.getAccessToken().isEmpty(), "User doesn't have access token");
        Assert.assertEquals(getUserView().getStatusCode(), StatusCode.OK.getCode(), "Expected status code to be 200(get UserView)");
        Assert.assertEquals(registerUser().getStatusCode(),StatusCode.CREATED.getCode(),"Expected status code to be 201(register user)");
        Assert.assertEquals(deleteUser().getStatusCode(),StatusCode.OK.getCode(),"Expected status code to be 201(delete user)");
        Assert.assertEquals(parseUser(getUserView()).getEmail(), getTestData("user_email"), "user email and email from test_data is not equal");
        Assert.assertEquals(getUserList().getStatusCode(), StatusCode.OK.getCode(), "Expected status code to be 200(get UserList)");
        Assert.assertTrue(checkIfGetUserListFilterWorks(parseUsersList(getUserList())),"filter is working incorrectly");
        Assert.assertEquals(getCategories().getStatusCode(),StatusCode.PARTIAL_RESPONSE.getCode(),"Expected status code to be 206(get Categories)");
        Assert.assertEquals(getCategory(parseCategoriesList(getCategories()).get(0).getCategoryId()).getStatusCode(),StatusCode.OK.getCode(),"Expected status code to be 200(get Category)");
        Assert.assertEquals(postCategory().getStatusCode(),StatusCode.CREATED.getCode(),"Expected status code to be 201(post category)");
//        Assert.assertEquals(putCategory().getStatusCode(),StatusCode.OK.getCode(),"Expected status code to be 200(put category)");
        Assert.assertEquals(deleteCategory().getStatusCode(),StatusCode.OK.getCode(),"Expected status code to be 200(delete category)");
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
