import io.qameta.allure.Description;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import org.testng.annotations.Test;

public class BrandControllerTest extends InventoryBaseTest{

    @Test(testName = "Test Brand controller")
    @Severity(SeverityLevel.NORMAL)
    @Description("Test various requests related to Brand based controller")
    public void BrandTest() {
        testSteps.getBrandAndBrandsList();
        testSteps.addEditAndDeleteBrand();
    }
}
