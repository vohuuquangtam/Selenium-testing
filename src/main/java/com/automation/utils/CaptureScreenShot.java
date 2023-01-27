package com.automation.utils;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.io.FileHandler;
import org.testng.Reporter;

import java.io.File;
import java.net.URL;

public class CaptureScreenShot {
    static String destination;
    public static String takeScreenshot(WebDriver driver, String userName)
    {
        try {
            //try to generate image folder
            File theDir = new File("Screenshots");
            if(!theDir.exists()){
                theDir.mkdirs();
            }
            //imageName = userName.png

            String imageName = userName +".png";

            // Capture screen shot
            TakesScreenshot screenshot = (TakesScreenshot) driver;
            File source = screenshot.getScreenshotAs(OutputType.FILE);
            destination = "./Screenshots/" + imageName;
            //generate a pic file in /ScreenShots
            File destiny = new File(destination);

            FileHandler.copy(source, destiny);
            Reporter.log("<br>  <img src='"+destination+"' height='100' width='100' /><br>");
            Reporter.log("<a href="+destination+"></a>");

        } catch (Exception ex) {
            System.out.println("Đã xảy ra lỗi khi chụp màn hình!");
            ex.printStackTrace();
        }
        return destination;
    }
}
