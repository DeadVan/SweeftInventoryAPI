import io.qameta.allure.Description;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import org.testng.annotations.Test;

public class CategoryControllerTest extends InventoryBaseTest {

    @Test(testName = "Test Category controller", priority = 2)
    @Severity(SeverityLevel.NORMAL)
    @Description("Test various requests related to category based controller")
    public void CategoryTest() {
        testSteps.getCategoryAndCategoryList();
        testSteps.addEditAndDeleteCategory();
    }
}
