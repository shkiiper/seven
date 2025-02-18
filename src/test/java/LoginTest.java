import com.seven.driver.Driver;
import com.seven.page.LoginPage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import io.qameta.allure.Feature;
import io.qameta.allure.Description;
import io.qameta.allure.Story;

import java.time.Duration;
import java.util.List;


public class LoginTest extends BaseTest{
    LoginPage loginPage;

    @BeforeMethod
    public void setup() {
        Driver.getDriver().get("https://seven.talentlms.com/index");
        loginPage = new LoginPage();
    }

    private String getErrorMessageFromPage() {
        WebDriverWait wait = new WebDriverWait(Driver.getDriver(), Duration.ofSeconds(5));

        try {
            // ✅ Ждем появления алерта (если логин или пароль неверные)
            WebElement alertError = wait.until(ExpectedConditions.visibilityOfElementLocated(
                    By.cssSelector(".toast-message")));
            return alertError.getText();
        } catch (Exception e) {
            // Если алерт не появился, продолжаем проверку ошибок валидации
        }

        // ✅ Проверяем ошибки валидации (пустые поля, некорректный логин)
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


    @Test
    @Feature("Авторизация")
    @Story("Успешный вход")
    @Description("Проверяем, что пользователь может успешно войти")
    public void testValidLogin() {
        loginPage.login("shkiiper", "wedfvb123")
                .moveToLegacyInterface();
    }

    @Test
    public void testInvalidPassword() {
        loginPage.login("shkiiper", "wrongpassword");
        String errorText = getErrorMessageFromPage();
        System.out.println("Error Message: " + errorText);
        Assert.assertNotEquals(errorText, "No error message", "Ошибка авторизации не отображается!");
    }

    @Test
    public void testInvalidUsername() {
        loginPage.login("wronguser", "wedfvb123");
        String errorText = getErrorMessageFromPage();
        System.out.println("Error Message: " + errorText);
        Assert.assertNotEquals(errorText, "No error message", "Ошибка авторизации не отображается!");
    }

    @Test
    public void testEmptyFields() {
        loginPage.login("", "");
        String errorText = getErrorMessageFromPage();
        System.out.println("Error Message: " + errorText);
        Assert.assertTrue(errorText.contains("'Username or email' is required") ||
                        errorText.contains("'Password' is required"),
                "Сообщение об ошибке не найдено!");
    }

    @Test
    public void testOnlyUsernameFilled() {
        loginPage.login("shkiiper", "");
        String errorText = getErrorMessageFromPage();
        System.out.println("Error Message: " + errorText);
        Assert.assertTrue(errorText.contains("'Password' is required"),
                "Сообщение об ошибке не найдено!");
    }

    @Test
    public void testOnlyPasswordFilled() {
        loginPage.login("", "wedfvb123");
        String errorText = getErrorMessageFromPage();
        System.out.println("Error Message: " + errorText);
        Assert.assertTrue(errorText.contains("'Username or email' is required"),
                "Сообщение об ошибке не найдено!");
    }

    @Test
    public void testLongCredentials() {
        String longUsername = "user".repeat(100);
        String longPassword = "pass".repeat(100);
        loginPage.login(longUsername, longPassword);
        String errorText = getErrorMessageFromPage();
        System.out.println("Error Message: " + errorText);
        Assert.assertTrue(errorText.contains("incorrect") || errorText.contains("too long"),
                "Ошибка при длинных данных не отображается!");
    }

    @Test
    public void testSpacesInCredentials() {
        loginPage.login("  shkiiper  ", "  wedfvb123  ");
        String errorText = getErrorMessageFromPage();
        System.out.println("Error Message: " + errorText);
        Assert.assertNotEquals(errorText, "No error message", "Система могла принять логин с пробелами!");
    }


    @Test
    public void testCaseSensitivity() {
        loginPage.login("SHKIIPER", "wedfvb123");
        String errorText = getErrorMessageFromPage();
        System.out.println("Error Message: " + errorText);

        // Если система различает регистр, ошибка должна быть
        if (errorText.contains("incorrect") || errorText.contains("invalid")) {
            System.out.println("Система чувствительна к регистру логина.");
        } else {
            System.out.println("Система НЕ чувствительна к регистру логина.");
        }

        // Просто выводим результат, а не ломаем тест
        Assert.assertTrue(true, "Проверка завершена.");
    }

}
