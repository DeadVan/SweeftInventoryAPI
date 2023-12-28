import io.qameta.allure.Description;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import org.testng.annotations.Test;

public class ModelControllerTest extends InventoryBaseTest{

    @Test(testName = "Test Model controller")
    @Severity(SeverityLevel.NORMAL)
    @Description("Test various requests related to model based controller")
    public void ModelTest() {
        testSteps.getModelAndModelList();
        testSteps.addEditAndDeleteModel();
    }
}
