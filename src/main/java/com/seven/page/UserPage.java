package com.seven.page;

import com.seven.helper.WebElementActions;
import com.seven.driver.Driver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class UserPage extends BasePage {
    WebElementActions actions = new WebElementActions();

    @FindBy(xpath = "//a[contains(text(),'Add user')]")
    private WebElement addUserButton;

    @FindBy(name = "name")
    private WebElement firstNameInput;

    @FindBy(name = "surname")
    private WebElement lastNameInput;

    @FindBy(name = "email")
    private WebElement emailInput;

    @FindBy(name = "login")
    private WebElement usernameInput;

    @FindBy(name = "password")
    private WebElement passwordInput;

    @FindBy(name = "submit_personal_details")
    private WebElement submitButton;

    public UserPage() {
        PageFactory.initElements(Driver.getDriver(), this);
    }

    public void goToAddUser() {
        actions.click(addUserButton);
    }

    public void createUser(String firstName, String lastName, String email, String username, String password) {
        actions.sendKeys(firstNameInput, firstName);
        actions.sendKeys(lastNameInput, lastName);
        actions.sendKeys(emailInput, email);
        actions.sendKeys(usernameInput, username);
        actions.sendKeys(passwordInput, password);
        actions.click(submitButton);
    }
}
