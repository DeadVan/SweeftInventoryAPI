import io.qameta.allure.Description;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import org.testng.annotations.Test;

public class UserControllerTest extends InventoryBaseTest{

    @Test(testName = "Test User controller",priority = 1)
    @Severity(SeverityLevel.BLOCKER)
    @Description("Test various requests related to User based controller")
    public void UserTest() {
        testSteps.changePasswordAndThanResetAsItWaS();
        testSteps.getUserDetailsAndValidate();
        testSteps.getUserListAndCheckFiltering();
        testSteps.addUserAndDelete();
    }

}
