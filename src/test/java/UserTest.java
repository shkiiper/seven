import com.github.javafaker.Faker;
import com.seven.page.DashboardPage;
import com.seven.page.UserPage;
import com.seven.driver.Driver;
import com.seven.page.LoginPage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.time.Duration;
import java.util.List;

public class UserTest extends BaseTest {
    private LoginPage loginPage;
    private DashboardPage dashboardPage;
    private UserPage userPage;
    private Faker faker;

    @BeforeMethod
    public void setUp() {
        Driver.getDriver().get("https://seven.talentlms.com/index");

        loginPage = new LoginPage();
        dashboardPage = new DashboardPage();
        userPage = new UserPage();
        faker = new Faker();
        loginPage.login("shkiiper", "wedfvb123")
                .moveToLegacyInterface();

    }

    private String getErrorMessage() {
        WebDriverWait wait = new WebDriverWait(Driver.getDriver(), Duration.ofSeconds(5));

        try {
            // ✅ Проверяем алерт (если пользователь не создан из-за ошибки)
            WebElement alertError = wait.until(ExpectedConditions.visibilityOfElementLocated(
                    By.cssSelector(".toast-message")));
            return alertError.getText();
        } catch (Exception e) {
            // Если алерт не появился, продолжаем проверку ошибок валидации
        }

        // ✅ Проверяем ошибки валидации полей
        List<WebElement> fieldErrors = Driver.getDriver().findElements(By.cssSelector(".help-inline"));
        if (!fieldErrors.isEmpty()) {
            StringBuilder errorMessage = new StringBuilder();
            for (WebElement error : fieldErrors) {
                errorMessage.append(error.getText()).append("\n");
            }
            return errorMessage.toString().trim();
        }

        return "No error message";
    }


    // ✅ 1. Позитивный тест: создание пользователя с валидными данными
    @Test
    public void testCreateUser() {
        String firstName = faker.name().firstName();
        String lastName = faker.name().lastName();
        String email = faker.internet().emailAddress();
        String username = faker.name().username();
        String password = "Test@12345";

        userPage.goToAddUser();
        userPage.createUser(firstName, lastName, email, username, password);
        System.out.println("User created: " + username);
    }

    // ✅ 2. Создание пользователя только с обязательными полями
    @Test
    public void testCreateUserWithMinimumData() {
        String firstName = faker.name().firstName();
        String email = faker.internet().emailAddress();
        String username = faker.name().username();
        String password = "Test@1234";

        userPage.goToAddUser();
        userPage.createUser(firstName, "", email, username, password);
        System.out.println("User created with minimum data: " + username);
    }

    // ❌ 3. Создание пользователя с уже существующим email
    @Test
    public void testCreateUserWithExistingEmail() {
        userPage.goToAddUser();
        userPage.createUser("John", "Doe", "existinguser@example.com", faker.name().username(), "Test@1234");

        String errorMessage = getErrorMessage();
        System.out.println("Error Message: " + errorMessage);
        Assert.assertTrue(errorMessage.contains("already exists") || errorMessage.contains("Email address is required"),
                "Ошибка email не отображается!");
    }

    // ❌ 4. Создание пользователя с уже существующим username
    @Test
    public void testCreateUserWithExistingUsername() {
        userPage.goToAddUser();
        userPage.createUser("John", "Doe", faker.internet().emailAddress(), "existingUsername", "Test@1234");

        String errorMessage = getErrorMessage();
        System.out.println("Error Message: " + errorMessage);
        Assert.assertTrue(errorMessage.contains("already exists") || errorMessage.contains("Username is required"),
                "Ошибка username не отображается!");
    }

    // ❌ 5. Создание пользователя без email
    @Test
    public void testCreateUserWithoutEmail() {
        String firstName = faker.name().firstName();
        String lastName = faker.name().lastName();
        String username = faker.name().username();
        String password = "Test@1234";

        userPage.goToAddUser();
        userPage.createUser(firstName, lastName, "", username, password);

        String errorMessage = getErrorMessage();
        System.out.println("Error Message: " + errorMessage);
        Assert.assertTrue(errorMessage.contains("email is required"), "Ошибка не отображается!");
    }

    // ❌ 6. Создание пользователя с невалидным email
    @Test
    public void testCreateUserWithInvalidEmail() {
        String firstName = faker.name().firstName();
        String lastName = faker.name().lastName();
        String username = faker.name().username();
        String password = "Test@1234";

        userPage.goToAddUser();
        userPage.createUser(firstName, lastName, "invalidemail", username, password);

        String errorMessage = getErrorMessage();
        System.out.println("Error Message: " + errorMessage);
        Assert.assertTrue(errorMessage.contains("invalid email"), "Ошибка не отображается!");
    }

    // ❌ 7. Создание пользователя с коротким паролем
    @Test
    public void testCreateUserWithShortPassword() {
        userPage.goToAddUser();
        userPage.createUser("John", "Doe", faker.internet().emailAddress(), faker.name().username(), "123");

        String errorMessage = getErrorMessage();
        System.out.println("Error Message: " + errorMessage);
        Assert.assertTrue(errorMessage.contains("too short") || errorMessage.contains("Password is required"),
                "Ошибка пароля не отображается!");
    }

    // ❌ 8. SQL-инъекция в username
    @Test
    public void testCreateUserWithSqlInjection() {
        userPage.goToAddUser();
        userPage.createUser("SQL", "Injection", "sql@example.com", "' OR 1=1 --", "Test@1234");

        String errorMessage = getErrorMessage();
        System.out.println("Error Message: " + errorMessage);
        Assert.assertTrue(errorMessage.contains("invalid"), "SQL-инъекция могла сработать!");
    }

    // ❌ 9. XSS-атака через username
    @Test
    public void testCreateUserWithXssInjection() {
        userPage.goToAddUser();
        userPage.createUser("XSS", "Test", "xss@example.com", "<script>alert('XSS')</script>", "Test@1234");

        String errorMessage = getErrorMessage();
        System.out.println("Error Message: " + errorMessage);
        Assert.assertTrue(errorMessage.contains("invalid"), "XSS-атака могла сработать!");
    }
}
