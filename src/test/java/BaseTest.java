import com.seven.driver.Driver;
import com.seven.helper.AlertHelper;
import com.seven.helper.WebElementActions;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.BeforeSuite;

public class BaseTest {

    public WebDriver driver;
    WebElementActions webElementActions;
    AlertHelper alertHelper;

    @BeforeSuite
    public void beforeSuite() {
        driver = Driver.getDriver();
        webElementActions = new WebElementActions();
        alertHelper = new AlertHelper(driver);
    }
}
