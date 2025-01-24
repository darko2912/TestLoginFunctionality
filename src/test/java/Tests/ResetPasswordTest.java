package Tests;

import Base.BaseTest;
import Base.ExcelReader;
import Base.RetryAnalyzer;
import Helpers.Data;
import Helpers.WebDriverFactory;
import Pages.ResetPasswordPage;
import Pages.LoginPage;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.io.IOException;
import java.time.Duration;

import static Helpers.Data.*;
import static Helpers.URLs.*;

public class ResetPasswordTest extends BaseTest {

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
        resetPasswordPage = new ResetPasswordPage();
    }

    @Test (priority = 10, retryAnalyzer = RetryAnalyzer.class)
    public void userCanSuccessfullyRedirectedToResetPasswordPageAndRecoveryPassword(){
        loginPage.clickOnForgottenPasswordLink();

        wait.until(ExpectedConditions.urlToBe(resetPasswordURL));
        Assert.assertEquals(driver.getCurrentUrl(), resetPasswordURL);

        resetPasswordPage.inputEmail(validEmail);
        resetPasswordPage.clickOnSendButton();

        wait.until(ExpectedConditions.visibilityOf(resetPasswordPage.alertMessage));
        Assert.assertTrue(resetPasswordPage.alertMessageIsVisible());
        Assert.assertEquals(resetPasswordPage.alertMessage.getText(), alertSuccessMessage);

        resetPasswordPage.clickOnGoToLoginButton();

        wait.until(ExpectedConditions.urlToBe(loginURL));
        Assert.assertEquals(driver.getCurrentUrl(), loginURL);

    }

    @Test (priority = 20, retryAnalyzer = RetryAnalyzer.class)
    public void userCannotResetPasswordWithEmptyEmailOnResetPasswordPage(){
        loginPage.clickOnForgottenPasswordLink();
        resetPasswordPage.clickOnSendButton();

        wait.until(ExpectedConditions.visibilityOf(resetPasswordPage.errorMessage));
        Assert.assertTrue(resetPasswordPage.errorMessageIsVisible());
        Assert.assertEquals(resetPasswordPage.errorMessage.getText(), requiredMessage);
    }

    @Test (priority = 30, retryAnalyzer = RetryAnalyzer.class)
    public void userCannotResetPasswordWithUnregisteredEmail(){
        loginPage.clickOnForgottenPasswordLink();
        resetPasswordPage.inputEmail("darko.ocel@hotmail.com");
        resetPasswordPage.clickOnSendButton();

        wait.until(ExpectedConditions.visibilityOf(resetPasswordPage.alertMessage));
        Assert.assertTrue(resetPasswordPage.alertMessageIsVisible());
        Assert.assertEquals(resetPasswordPage.alertMessage.getText(), notFoundUser);
    }

    @Test (priority = 40,retryAnalyzer = RetryAnalyzer.class)
    public void userCannotResetPasswordWithInvalidFormatEmail(){
        for (int i=1; i<=excelReader.getLastRow("ResetPasswordData"); i++){
            loginPage.clickOnForgottenPasswordLink();
            String invalidEmail = excelReader.getStringData("ResetPasswordData",i,0);
            resetPasswordPage.inputEmail(invalidEmail);
            resetPasswordPage.clickOnSendButton();

            Assert.assertTrue(resetPasswordPage.errorMessageIsVisible());
            Assert.assertEquals(resetPasswordPage.errorMessage.getText(), Data.invalidEmail);

            driver.navigate().to(loginURL);
        }
    }

    @AfterMethod
    public void tearDownTest(){
        driver.manage().deleteAllCookies();
        driver.quit();
    }
}
