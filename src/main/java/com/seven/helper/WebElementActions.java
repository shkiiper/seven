package com.seven.helper;

import com.seven.driver.Driver;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

public class WebElementActions {

    public Actions actions=new Actions(Driver.getDriver());

    public WebElementActions jsClick (WebElement element)
    {
        waitElementToBeDisplayed(element);
        JavascriptExecutor js = (JavascriptExecutor) Driver.getDriver();
        js.executeScript("arguments[0].click();", element);
        return this;
    }

    public WebElementActions waitButtonToBeClickAble(WebElement element)
    {
        new WebDriverWait(Driver.getDriver(), Duration.ofSeconds(15))
                .until(ExpectedConditions.elementToBeClickable(element));
        return this;
    }

    public WebElementActions waitElementToBeDisplayed(WebElement element)
    {
        new WebDriverWait(Driver.getDriver(), Duration.ofSeconds(15))
                .until(ExpectedConditions.visibilityOf(element));
        return this;
    }

    public WebElementActions click(WebElement element){
        waitElementToBeDisplayed(element);
        waitButtonToBeClickAble(element);
        element.click();
        return this;
    }

    public WebElementActions sendKeys(WebElement element, String text)
    {
        waitElementToBeDisplayed(element);
        element.sendKeys(text);
        return this;
    }

    public WebElementActions doubleClick(WebElement element){
        waitElementToBeDisplayed(element);
        waitButtonToBeClickAble(element);
        actions.doubleClick(element).perform();
        return this;
    }

    public WebElementActions rightClick(WebElement element){
        waitElementToBeDisplayed(element);
        waitButtonToBeClickAble(element);
        actions.contextClick(element).perform();// perform всегда после методов actions
        return this;
    }

    public WebElementActions moveToElement(WebElement element){
        waitElementToBeDisplayed(element);
        actions.moveToElement(element).perform();// perform всегда после методов actions
        return this;
    }

    public WebElementActions expandAllFolders() {
        while (true) {
            // Получаем все закрытые папки
            List<WebElement> collapsedFolders = Driver.getDriver()
                    .findElements(By.cssSelector(".rct-node.rct-node-collapsed .rct-collapse-btn"));

            if (collapsedFolders.isEmpty()) break; // Выходим, если все папки раскрыты

            for (WebElement button : collapsedFolders) {
                click(button);
                waitElementToBeDisplayed(button);
            }
        }
        return this;
    }

    public WebElementActions selectAllCheckBoxes(List<WebElement> checkBoxes) {
        for (int i = checkBoxes.size() - 1; i >= 0; i--) { // Проходим список снизу вверх
            waitElementToBeDisplayed(checkBoxes.get(i));
            click(checkBoxes.get(i));
        }
        return this;
    }

    public boolean isElementDisplayed(WebElement element) {
        try {
            return element.isDisplayed();
        } catch (Exception e) {
            return false; // Если элемент не найден, просто возвращаем false
        }
    }


}
