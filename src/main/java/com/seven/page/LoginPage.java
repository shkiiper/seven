package com.seven.page;

import com.seven.helper.WebElementActions;
import com.seven.driver.Driver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class LoginPage {
    WebElementActions actions = new WebElementActions();

    @FindBy(name = "login")
    private WebElement login;

    @FindBy(name = "password")
    private WebElement password;

    @FindBy(name = "submit")
    private WebElement loginButton;

    public LoginPage() {
        PageFactory.initElements(Driver.getDriver(), this);
    }

    public DashboardPage login(String username, String password) {
        actions.sendKeys(login, username)
                .sendKeys(this.password, password)
                .click(loginButton);
        return new DashboardPage();
    }
}
