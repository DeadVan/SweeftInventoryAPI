import io.qameta.allure.Step;
import org.testng.Assert;
import restAPI.UnauthorizedReqs;
import utils.StatusCode;

import static restAPI.BrandReqs.*;
import static restAPI.BrandReqs.deleteBrand;
import static restAPI.CategoryReqs.*;
import static restAPI.ItemReqs.*;
import static restAPI.ItemReqs.deleteItem;
import static restAPI.ModelReqs.*;
import static restAPI.ModelReqs.deleteModel;
import static restAPI.UserReqs.*;
import static restAPI.UserReqs.getUserView;
import static utils.DataReader.getTestData;
import static utils.ParseUtil.*;
import static utils.ResponseUtils.*;

public class InventoryTestSteps {

    @Step("change Password And Than Reset As It WaS")
    public void changePasswordAndThanResetAsItWaS(){
        Assert.assertEquals(changePassword().getStatusCode(), StatusCode.OK.getCode());
        Assert.assertEquals(resetPassword().getStatusCode(),StatusCode.OK.getCode());
    }

    public void getUserDetailsAndValidate(){
        Assert.assertEquals(getUserView().getStatusCode(), StatusCode.OK.getCode());
        Assert.assertEquals(parseUser(getUserView()).getEmail(), getTestData("user_email"), "user email and email from test_data is not equal");
    }

    public void getUserListAndCheckFiltering(){
        Assert.assertEquals(getUserList().getStatusCode(), StatusCode.OK.getCode());
        Assert.assertTrue(checkIfGetUserListFilterWorks(parseUsersList()),"filter is working incorrectly");
    }

    public void addUserAndDelete(){
        Assert.assertEquals(registerUser().getStatusCode(),StatusCode.CREATED.getCode());
        Assert.assertEquals(deleteUser().getStatusCode(),StatusCode.OK.getCode());
    }
    public void getBrandAndBrandsList(){
        Assert.assertEquals(getBrands().getStatusCode(), StatusCode.OK.getCode());
        Assert.assertEquals(getRandomBrand(parseBrandList()), StatusCode.OK.getCode());
    }
    public void addEditAndDeleteBrand(){
        Assert.assertEquals(postBrand().getStatusCode(), StatusCode.CREATED.getCode());
        Assert.assertEquals(putBrand().getStatusCode(), StatusCode.OK.getCode());
        Assert.assertEquals(deleteBrand().getStatusCode(), StatusCode.OK.getCode());
    }
    public void getCategoryAndCategoryList(){
        Assert.assertEquals(getCategories().getStatusCode(), StatusCode.PARTIAL_RESPONSE.getCode());
        Assert.assertEquals(getRandomCategory(parseCategoriesList()),StatusCode.OK.getCode());
    }
    public void addEditAndDeleteCategory(){
        Assert.assertEquals(postCategory().getStatusCode(),StatusCode.CREATED.getCode());
        Assert.assertEquals(putCategory().getStatusCode(),StatusCode.OK.getCode());
        Assert.assertEquals(putCategoryIcon().getStatusCode(),StatusCode.OK.getCode());
        Assert.assertEquals(deleteCategory().getStatusCode(),StatusCode.OK.getCode());
    }
    public void getItemAndItemList(){
        Assert.assertEquals(getItems().getStatusCode(), StatusCode.OK.getCode());
        Assert.assertEquals(getRandomItem(parseItemList()),StatusCode.OK.getCode());
    }
    public void addEditAndDeleteItem(){
        Assert.assertEquals(postItem().getStatusCode(),StatusCode.CREATED.getCode());
        Assert.assertEquals(putItem().getStatusCode(),StatusCode.OK.getCode());
        Assert.assertEquals(deleteItem().getStatusCode(),StatusCode.OK.getCode());
    }
    public void getModelAndModelList(){
        Assert.assertEquals(getModels().getStatusCode(), StatusCode.OK.getCode());
        Assert.assertEquals(getRandomModel(parseModelList()), StatusCode.OK.getCode());
    }
    public void addEditAndDeleteModel(){
        Assert.assertEquals(postModel().getStatusCode(), StatusCode.CREATED.getCode());
        Assert.assertEquals(putModel().getStatusCode(), StatusCode.OK.getCode());
        Assert.assertEquals(deleteModel().getStatusCode(), StatusCode.OK.getCode());
    }
    public void unauthorizedRequestsForSafety(String... getEndpoint){
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
}
