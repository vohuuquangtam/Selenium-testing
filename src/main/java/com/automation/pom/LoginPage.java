package com.automation.pom;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.WebDriverWait;

public class LoginPage {
    private WebDriver driver;
    @FindBy(id="email") WebElement emailTextbox;
    @FindBy(id="passwd") WebElement passwdTextbox;
    @FindBy(id="SubmitLogin") WebElement signinBtn;

    //constructor
    public LoginPage(WebDriver driver) {
       WebDriverWait wait = new WebDriverWait(driver,30);
    }
    public void enterEmail(String email) throws InterruptedException{
        emailTextbox.sendKeys(email);
        Thread.sleep(3000);
    }
    public void enterPassword(String password) throws  InterruptedException{
        passwdTextbox.sendKeys(password);
        Thread.sleep(3000);
    }
    public void clickSignIn() throws InterruptedException{
//        Actions actions = new Actions(driver);
//        SoftAssert softAssert = new SoftAssert();
//        actions.moveToElement(signinBtn).build().perform();
//        softAssert.assertEquals(true,signinBtn.isDisplayed(),"btn Signin has not been displayed");
        signinBtn.click();
        Thread.sleep(3000);
    }


}
