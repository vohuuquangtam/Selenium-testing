package com.automation.base;

import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;

import java.util.concurrent.TimeUnit;

public class DriverInstance {
    protected ChromeDriver driver;

    @BeforeClass
    public void initDriverInstance() {
        System.out.println("Start: Open browser");
        System.setProperty("webdriver.chrome.driver", "./browserDrivers/chromedriver_win32/chromedriver.exe");
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.manage().timeouts().pageLoadTimeout(30, TimeUnit.SECONDS);

    }

    @AfterClass
    public void closeDriverInstance(){
        System.out.println("finish: Close browser");
        driver.close();
    }
}
