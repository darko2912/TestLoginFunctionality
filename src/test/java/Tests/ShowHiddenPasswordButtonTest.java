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

public class ShowHiddenPasswordButtonTest extends BaseTest {

    @BeforeMethod
    public void pageSetUp() throws IOException {
        chooseTheBrowser("edge");
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

    @Test(priority = 10, retryAnalyzer = RetryAnalyzer.class)
    public void verifyThePasswordIsHiddenByDefaultWhenTyped(){
        loginPage.inputEmail(validEmail);
        loginPage.inputPassword(validPassword);

        Assert.assertEquals(loginPage.passwordField.getAttribute("type"), "password");
        Assert.assertTrue(loginPage.showHiddenButton.getAttribute("class").contains("mdi-eye-off"));
    }

    @Test (priority = 20, retryAnalyzer = RetryAnalyzer.class)
    public void verifyThePasswordBecomesVisibleWhenClickingShowHiddenButton(){
        loginPage.inputEmail(validEmail);
        loginPage.inputPassword(validPassword);
        loginPage.clickOnShowHiddenButton();

        Assert.assertEquals(loginPage.passwordField.getAttribute("type"), "text");
        Assert.assertFalse(loginPage.showHiddenButton.getAttribute("class").contains("mdi-eye-off"));
    }

    @Test (priority = 30, retryAnalyzer = RetryAnalyzer.class)
    public void verifyThePasswordBecomesAgainHiddenWhenClickingShowHiddenButton(){
        loginPage.inputEmail(validEmail);
        loginPage.inputPassword(validPassword);
        loginPage.clickOnShowHiddenButton();

        Assert.assertEquals(loginPage.passwordField.getAttribute("type"), "text");
        Assert.assertFalse(loginPage.showHiddenButton.getAttribute("class").contains("mdi-eye-off"));

        loginPage.clickOnShowHiddenButton();

        Assert.assertEquals(loginPage.passwordField.getAttribute("type"), "password");
        Assert.assertTrue(loginPage.showHiddenButton.getAttribute("class").contains("mdi-eye-off"));
    }

    @Test ( priority = 40, retryAnalyzer = RetryAnalyzer.class)
    public void userCanLoginWhenPasswordIsShown(){
        loginPage.inputEmail(validEmail);
        loginPage.inputPassword(validPassword);
        loginPage.clickOnShowHiddenButton();

        Assert.assertEquals(loginPage.passwordField.getAttribute("type"), "text");
        Assert.assertFalse(loginPage.showHiddenButton.getAttribute("class").contains("mdi-eye-off"));

        loginPage.clickOnLoginButton();

        wait.until(ExpectedConditions.urlToBe(otpValidateURL));
        Assert.assertEquals(driver.getCurrentUrl(), otpValidateURL);
        Assert.assertEquals(otpValidatePage.otpValidateTitle.getText(), otpTitle);
    }

    @Test (priority = 50, retryAnalyzer = RetryAnalyzer.class)
    public void verifyTheVeryLongPasswordBecomesVisibleAndHiddenWhenClickingShowHiddenButton(){
        loginPage.inputEmail(validEmail);
        loginPage.inputPassword(fakerLongPassword());
        loginPage.clickOnShowHiddenButton();

        Assert.assertEquals(loginPage.passwordField.getAttribute("type"), "text");
        Assert.assertFalse(loginPage.showHiddenButton.getAttribute("class").contains("mdi-eye-off"));

        loginPage.clickOnShowHiddenButton();

        Assert.assertEquals(loginPage.passwordField.getAttribute("type"), "password");
        Assert.assertTrue(loginPage.showHiddenButton.getAttribute("class").contains("mdi-eye-off"));

    }

    @AfterMethod
    public void tearDownTest(){
        driver.manage().deleteAllCookies();
        driver.quit();
    }
}
