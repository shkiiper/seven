import com.seven.page.DashboardPage;
import com.seven.driver.Driver;
import com.seven.page.LoginPage;
import com.seven.page.Sections;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class DashboardTest extends BaseTest{
    private LoginPage loginPage;
    private DashboardPage dashboardPage;

    @BeforeClass
    public void setUp() {
        Driver.getDriver().get("https://seven.talentlms.com/index");

        loginPage = new LoginPage();
        dashboardPage = new DashboardPage();
        loginPage.login("shkiiper", "wedfvb123")
                .moveToLegacyInterface();
    }


    @Test
    public void testNavigation() {
//        dashboardPage.goToUsersList(); // Открываем Users
//        dashboardPage.goToAddUser(); // Открываем Add User
//
        dashboardPage.navigateToSection(Sections.Section.COURSES);
//        dashboardPage.navigateToSection(Section.CATEGORIES);
//        dashboardPage.navigateToSection(Section.GROUPS);
//        dashboardPage.navigateToSection(Section.BRANCHES);
//        dashboardPage.navigateToSection(Section.EVENTS_ENGINE);
//        dashboardPage.navigateToSection(Section.USER_TYPES);
//        dashboardPage.navigateToSection(Section.IMPORT_EXPORT);
//        dashboardPage.navigateToSection(Section.REPORTS);
//        dashboardPage.navigateToSection(Section.ACCOUNT_SETTINGS);
    }
    }
