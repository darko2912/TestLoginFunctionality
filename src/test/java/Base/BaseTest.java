package Base;

import Pages.ResetPasswordPage;
import Pages.LoginPage;
import Pages.OtpValidatePage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;

import java.io.IOException;

public class BaseTest {

    public static WebDriver driver;
    public static WebDriverWait wait;

    public static ExcelReader excelReader;
    public static LoginPage loginPage;
    public static OtpValidatePage otpValidatePage;
    public static ResetPasswordPage resetPasswordPage;

    @BeforeClass
    public void setUp() throws IOException {
        excelReader = new ExcelReader("TestData.xlsx");
        loginPage = new LoginPage();
        otpValidatePage = new OtpValidatePage();
        resetPasswordPage = new ResetPasswordPage();
    }

    @AfterClass
    public void tearDownProcess(){
        driver.quit();
    }

    public void chooseTheBrowser(String browserName){
        if (browserName.equalsIgnoreCase("firefox")){
            System.setProperty("browser", "firefox");
        } else if (browserName.equalsIgnoreCase("edge")) {
            System.setProperty("browser", "edge");
        } else {
            System.setProperty("browser", "");
        }
    }

}
