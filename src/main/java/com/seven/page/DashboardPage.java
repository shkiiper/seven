package com.seven.page;

import com.seven.helper.WebElementActions;
import com.seven.driver.Driver;
import org.openqa.selenium.By;
import com.seven.page.Sections.Section;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;

public class DashboardPage extends BasePage {
    WebElementActions actions = new WebElementActions();

    @FindBy(css = ".profile-menu-button")
    private WebElement profilemenu;

    @FindBy(xpath = "//a[@data-testid='legacy-menu-item']")
    private WebElement legacyInterface;

    @FindBy(xpath = "//a[contains(text(),'Add user')]")
    private WebElement addUserButton;

    public DashboardPage() {
        PageFactory.initElements(Driver.getDriver(), this);
    }

    public DashboardPage moveToLegacyInterface() {
        WebDriverWait wait = new WebDriverWait(Driver.getDriver(), Duration.ofSeconds(30));

        try {
            // ✅ Ожидаем появление и кликаем на кнопку профиля
            WebElement profileButton = wait.until(ExpectedConditions.elementToBeClickable(
                    By.cssSelector("[data-testid='profile-menu-button']")));
            profileButton.click();
            System.out.println("✅ Клик по Profile Menu");

            // ✅ Ожидаем появление меню и кликаем на Legacy Interface
            WebElement legacyButton = wait.until(ExpectedConditions.elementToBeClickable(
                    By.xpath("//a[@data-testid='legacy-menu-item']")));
            legacyButton.click();
            System.out.println("✅ Клик по Legacy Interface");

        } catch (TimeoutException e) {
            System.out.println("⚠️ Не удалось перейти в Legacy Interface: " + e.getMessage());
            e.printStackTrace();
        }

        return this;
    }



    public void navigateToSection(Section section) {
        WebDriverWait wait = new WebDriverWait(Driver.getDriver(), Duration.ofSeconds(15));
        WebElement sectionElement = Driver.getDriver().findElement(By.xpath(
                "//div[@id='tl-admin-dashboard']//div[contains(@onclick, '" + section.getPath() + "')]"
        ));
        actions.click(sectionElement);
    }

    public void goToUsersList() {
        navigateToSection(Section.USERS);
    }

    public void goToAddUser() {
        actions.click(addUserButton);
    }
}
