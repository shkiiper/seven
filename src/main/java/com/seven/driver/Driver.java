package com.seven.driver;

import com.seven.fileUtils.ConfigReader;
import org.openqa.selenium.WebDriver;

public class Driver {
    private Driver()
    {
        //Singleton pattenr
    }
    private static WebDriver driver;
    public static WebDriver getDriver()
    {
        if (driver == null)
        {
            switch (ConfigReader.getProperty("browserType").toLowerCase()){
                case "chrome":
                    driver=ChromeWebDriver.loadChromeWebDriver();
                    break;
                case "firefox":
                    driver=FireFoxWebDriver.loadFireFoxDriver();
                    break;
                case "safari":
                    driver= SafariWebDriver.loadSafariDriver();
                    break;

                default:
                    throw new RuntimeException("Unsupported browser type "+ ConfigReader.getProperty("browserType"));
            }
        }
        return driver;
    }
}
