import io.qameta.allure.Description;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import org.testng.annotations.Test;

public class ItemControllerTest extends InventoryBaseTest {

    @Test(testName = "Test Item controller")
    @Severity(SeverityLevel.NORMAL)
    @Description("Test various requests related to item based controller")
    public void ItemTest() {
        testSteps.getItemAndItemList();
        testSteps.addEditAndDeleteItem();
    }
}
