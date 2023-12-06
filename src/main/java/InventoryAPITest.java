import org.testng.Assert;
import org.testng.annotations.Test;
import utils.StatusCode;

import static restAPI.UserController.*;
import static dto.LoginCrd.loginCrd;
import static utils.DataReader.getTestData;
import static utils.ParseUtil.parseUser;
import static utils.ParseUtil.parseUsersList;
import static utils.ResponseUtils.checkIfGetUserListFilterWorks;

public class InventoryAPITest {

    @Test
    public void testLogin() {
        Assert.assertEquals(postLogin().getStatusCode(), StatusCode.OK.getCode(), "Expected status code to be 200");
        Assert.assertFalse(loginCrd.getAccessToken().isEmpty(), "User doesn't have access token");
        Assert.assertEquals(getUserView().getStatusCode(), StatusCode.OK.getCode(), "Expected status code to be 200");
        Assert.assertEquals(parseUser(getUserView()).getEmail(), getTestData("user_email"), "user email and email from test_data is not equal");
        Assert.assertEquals(getUserList().statusCode(), StatusCode.OK.getCode(), "Expected status code to be 200");
        Assert.assertTrue(checkIfGetUserListFilterWorks(parseUsersList(getUserList())),"filter is working incorrectly");
    }
}
