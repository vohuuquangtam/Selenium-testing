package com.automation.testcase;

import com.automation.base.DriverInstance;
import com.automation.pom.LoginPage;
import com.automation.utils.CaptureScreenShot;
import com.automation.utils.PropertiesFileUtils;
import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import org.apache.commons.io.FileUtils;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.ITestResult;
import org.testng.Reporter;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Base64;

public class TC_LoginTest extends DriverInstance {

    @Test(dataProvider = "Excel",dataProviderClass = TC_LoginTest.class)
    public void TC01_LoginFirstAccount(String email, String password) throws InterruptedException {
        //Lấy URL từ properties file và tải trang.
        String URL = PropertiesFileUtils.getProperties("base_URL");
        String iconSignIn = PropertiesFileUtils.getProperties("iconSignIn");
        String iconSignOut= PropertiesFileUtils.getProperties("iconSignOut");
        System.out.println("Test Case Login");
        driver.get(URL);
        WebDriverWait wait = new WebDriverWait(driver, 30);
        //softAssertion page loading
        SoftAssert softAssert = new SoftAssert();
        String actualURLHomepage = driver.getCurrentUrl();
        softAssert.assertEquals(actualURLHomepage,URL,"Actual page url is not the same as expected");

        //Lấy định danh iconSignin từ properties file và tìm kiếm, click
        WebElement iconSignin = wait.until(ExpectedConditions.visibilityOfElementLocated(By.className(iconSignIn)));
        softAssert.assertTrue(iconSignin.isDisplayed(),"sign in is not available");
        iconSignin.click();

        LoginPage loginPage = new LoginPage(driver);
        PageFactory.initElements(driver, loginPage);
        //Thực hiện đăng nhập qua loginPage.
        loginPage.enterEmail(email);
        loginPage.enterPassword(password);
        loginPage.clickSignIn();

        //

        //Lấy định danh iconSignout từ properties,
        //  kiểm tra SignOut có hiển thị ko, nếu hiển thị thì click
        WebElement iconSignout = wait.until(ExpectedConditions.visibilityOfElementLocated(By.className(iconSignOut)));
        softAssert.assertTrue(iconSignout.isDisplayed(),"sign out is not available");
        iconSignout.click();
        Thread.sleep(2000);
    }


    @DataProvider(name="Excel")
    public Object[][] testDataGenerator() throws IOException {
        //đọc dữ liệu đầu vào từ file excel
        FileInputStream file = new FileInputStream("data/assignment2_data_test.xlsx");
        XSSFWorkbook workbook = new XSSFWorkbook(file);
        XSSFSheet loginSheet = workbook.getSheet("Login");
        int numberOfRowData= loginSheet.getPhysicalNumberOfRows();
       // System.out.println(numberOfRowData);

        Object data[][] = new Object[numberOfRowData][2];
        for(int i=0; i< numberOfRowData; i++){
            XSSFRow row = loginSheet.getRow(i);
            XSSFCell username = row.getCell(0);
            XSSFCell password = row.getCell(1);
            data[i][0] =username.getStringCellValue();
            data[i][1] =password.getStringCellValue();
        }
        return data;
    }


    @AfterMethod
    public void takeScreenshot(ITestResult result) throws InterruptedException {
        Thread.sleep(3000);
        //ITestResult để lấy trạng thái và tên và tham số của từng Test Case
        // phương thức này sẽ được gọi mỗi khi @Test thực thi xong,
        // ta có thể kiểm tra kết quả testcase taị đây.
        if (ITestResult.FAILURE == result.getStatus()) {
            try {
                //1. lây tham số (parameters) đầu vào của TC vừa chạy
                //email:0, password:1
                String email = (String)result.getParameters()[0];
                email = email.replaceAll(" ", "");
                email = email.substring(0,email.length()-10);
                System.out.println("Đã chụp màn hình: " + email);
                String path =  CaptureScreenShot.takeScreenshot(driver, email);
                //System.out.println(path);

            } catch (Exception e) {
                System.out.println("Lỗi xảy ra screenshot " + e.getMessage());
            }
        }
    }


}
