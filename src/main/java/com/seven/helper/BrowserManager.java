package com.seven.helper;

import org.openqa.selenium.WebDriver;

public class BrowserManager {

    private WebDriver driver;

    public BrowserManager(WebDriver driver){
        this.driver=driver;
    }

    public void navigateTo(final String URL){
        driver.navigate().to(URL);
    }
    public void goBack(){
        driver.navigate().back();
    }

    public void goForward(){
        driver.navigate().forward();
    }
    public void refresh(){
        driver.navigate().refresh();
    }
}
