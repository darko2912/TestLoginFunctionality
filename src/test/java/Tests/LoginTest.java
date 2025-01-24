package Tests;

import Base.BaseTest;
import Base.ExcelReader;
import Base.RetryAnalyzer;
import Helpers.WebDriverFactory;
import Pages.LoginPage;
import Pages.OtpValidatePage;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.io.IOException;
import java.time.Duration;

import static Helpers.Data.*;
import static Helpers.URLs.loginURL;
import static Helpers.URLs.otpValidateURL;

public class LoginTest extends BaseTest {

    @BeforeMethod
    public void pageSetUp() throws IOException {
        chooseTheBrowser("chrome");
        WebDriverFactory.setupDriver();
        driver = WebDriverFactory.createWebDriver();
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
        wait = new WebDriverWait(driver, Duration.ofSeconds(3));

        driver.navigate().to(loginURL);

        excelReader = new ExcelReader("TestData.xlsx");
        loginPage = new LoginPage();
        otpValidatePage = new OtpValidatePage();
    }

    @Test (priority = 10, retryAnalyzer = RetryAnalyzer.class)
    public void userCanLoginSuccessfullyWithValidCredentials(){
        loginPage.inputEmail(validEmail);
        loginPage.inputPassword(validPassword);
        loginPage.clickOnLoginButton();

        wait.until(ExpectedConditions.urlToBe(otpValidateURL));
        Assert.assertEquals(driver.getCurrentUrl(), otpValidateURL);
        Assert.assertEquals(otpValidatePage.otpValidateTitle.getText(), otpTitle);

    }

    @Test (priority = 20, retryAnalyzer = RetryAnalyzer.class)
    public void userCannotLoginWithEmptyFields(){
        loginPage.clickOnLoginButton();

        wait.until(ExpectedConditions.visibilityOf(loginPage.errorMessage));
        Assert.assertTrue(loginPage.errorMessageIsVisible());
        Assert.assertEquals(loginPage.contentErrorMessage.getText(), invalidData);
        Assert.assertEquals(driver.getCurrentUrl(), loginURL);
    }

    @Test (priority = 30, retryAnalyzer = RetryAnalyzer.class)
    public void userCannotLoginWithValidPasswordAndEmptyEmail(){
        loginPage.inputPassword(validPassword);
        loginPage.clickOnLoginButton();

        wait.until(ExpectedConditions.visibilityOf(loginPage.errorMessage));
        Assert.assertTrue(loginPage.errorMessageIsVisible());
        Assert.assertEquals(loginPage.contentErrorMessage.getText(), invalidData);
        Assert.assertEquals(driver.getCurrentUrl(), loginURL);
    }

    @Test (priority = 40, retryAnalyzer = RetryAnalyzer.class)
    public void userCannotLoginWithValidEmailAndEmptyPassword(){
        loginPage.inputEmail(validEmail);
        loginPage.clickOnLoginButton();

        wait.until(ExpectedConditions.visibilityOf(loginPage.errorMessage));
        Assert.assertTrue(loginPage.errorMessageIsVisible());
        Assert.assertEquals(loginPage.contentErrorMessage.getText(), invalidData);
        Assert.assertEquals(driver.getCurrentUrl(), loginURL);
    }

    @Test (priority = 50, retryAnalyzer = RetryAnalyzer.class)
    public void userCannotLoginWithValidEmailAndInvalidPassword() {
        for (int i = 1; i <= excelReader.getLastRow("LoginData"); i++) {
            String invalidPassword = excelReader.getStringData("LoginData", i,3);
            loginPage.inputEmail(validEmail);
            loginPage.inputPassword(invalidPassword);
            loginPage.clickOnLoginButton();

            wait.until(ExpectedConditions.visibilityOf(loginPage.errorMessage));
            Assert.assertTrue(loginPage.errorMessageIsVisible());
            Assert.assertEquals(loginPage.contentErrorMessage.getText(), invalidData);
            Assert.assertEquals(driver.getCurrentUrl(), loginURL);

            driver.navigate().refresh();
        }
    }

    @Test (priority = 60, retryAnalyzer = RetryAnalyzer.class)
    public void userCannotLoginWithValidPasswordAndInvalidEmail() {
        for (int i = 1; i < excelReader.getLastRow("LoginData"); i++) {
            String invalidEmail = excelReader.getStringData("LoginData", i,2);
            loginPage.inputEmail(invalidEmail);
            loginPage.inputPassword(validPassword);
            loginPage.clickOnLoginButton();

            wait.until(ExpectedConditions.visibilityOf(loginPage.errorMessage));
            Assert.assertTrue(loginPage.errorMessageIsVisible());
            Assert.assertEquals(loginPage.contentErrorMessage.getText(), invalidData);
            Assert.assertEquals(driver.getCurrentUrl(), loginURL);

            driver.navigate().refresh();
        }
    }

    @Test (priority = 70, retryAnalyzer = RetryAnalyzer.class)
    public void userCannotLoginWithTrailingSpaceInEmailInputAndValidPassword(){
        loginPage.inputEmail(validEmail+" ");
        loginPage.inputPassword(validPassword);
        loginPage.clickOnLoginButton();

        wait.until(ExpectedConditions.visibilityOf(loginPage.errorMessage));
        Assert.assertTrue(loginPage.errorMessageIsVisible());
        Assert.assertEquals(loginPage.contentErrorMessage.getText(), invalidData);
        Assert.assertEquals(driver.getCurrentUrl(), loginURL);
    }

    @Test (priority = 80, retryAnalyzer = RetryAnalyzer.class)
    public void userCannotLoginWithLeadingSpaceInEmailInputAndValidPassword(){
        loginPage.inputEmail(" "+validEmail);
        loginPage.inputPassword(validPassword);
        loginPage.clickOnLoginButton();

        wait.until(ExpectedConditions.visibilityOf(loginPage.errorMessage));
        Assert.assertTrue(loginPage.errorMessageIsVisible());
        Assert.assertEquals(loginPage.contentErrorMessage.getText(), invalidData);
        Assert.assertEquals(driver.getCurrentUrl(), loginURL);
    }

    @AfterMethod
    public void tearDownTest(){
        driver.manage().deleteAllCookies();
        driver.quit();
    }
}
