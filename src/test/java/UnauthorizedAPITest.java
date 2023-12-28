import io.qameta.allure.*;
import org.testng.annotations.Test;
import utils.DataProviderUtil;

@Epic("Inventory API")
@Feature("Inventory API Tests")
public class UnauthorizedAPITest {
    @Test(testName = "Test unauthorized requests", dataProviderClass = DataProviderUtil.class, dataProvider = "endpoint")
    @Severity(SeverityLevel.CRITICAL)
    @Description("Test unauthorized requests using different HTTP methods")
    public void testUnauthorizedRequests(String... getEndpoint){
        InventoryBaseTest.testSteps.unauthorizedRequestsForSafety(getEndpoint);
    }
}
