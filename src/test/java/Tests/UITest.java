package Tests;

import Base.BaseTest;
import Base.RetryAnalyzer;
import Helpers.WebDriverFactory;
import Pages.LoginPage;
import Pages.OtpValidatePage;
import Pages.ResetPasswordPage;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.time.Duration;

import static Helpers.Data.*;
import static Helpers.URLs.*;

public class UITest extends BaseTest {


    @BeforeMethod
    public void pageSetUp() {
        chooseTheBrowser("firefox");
        WebDriverFactory.setupDriver();
        driver = WebDriverFactory.createWebDriver();
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
        wait = new WebDriverWait(driver, Duration.ofSeconds(3));

        driver.navigate().to(loginURL);

        loginPage = new LoginPage();
        otpValidatePage = new OtpValidatePage();
        resetPasswordPage = new ResetPasswordPage();
    }

    @Test (priority = 10, retryAnalyzer = RetryAnalyzer.class)
    public void verifyThatAllElementsAreVisibleOnLoginPage(){
        Assert.assertTrue(loginPage.loginPageTitle.isDisplayed());
        Assert.assertEquals(loginPage.loginPageTitle.getText(), loginTitle);
        Assert.assertTrue(loginPage.emailField.isDisplayed());
        Assert.assertTrue(loginPage.passwordField.isDisplayed());
        Assert.assertTrue(loginPage.loginButton.isDisplayed());
        Assert.assertTrue(loginPage.forgottenPasswordLink.isDisplayed());
        Assert.assertTrue(loginPage.showHiddenButton.isDisplayed());
    }

    @Test (priority = 20, retryAnalyzer = RetryAnalyzer.class)
    public void verifyThatAllElementsAreVisibleOnOtpValidatePage(){
        loginPage.inputEmail(validEmail);
        loginPage.inputPassword(validPassword);
        loginPage.clickOnLoginButton();

        wait.until(ExpectedConditions.urlToBe(otpValidateURL));
        Assert.assertTrue(otpValidatePage.otpValidateTitle.isDisplayed());
        Assert.assertEquals(otpValidatePage.otpValidateTitle.getText(), otpTitle);
        Assert.assertTrue(otpValidatePage.otpField.isDisplayed());
        Assert.assertTrue(otpValidatePage.verifyButton.isDisplayed());
        Assert.assertTrue(otpValidatePage.resendOtpButton.isDisplayed());
    }

    @Test (priority = 30, retryAnalyzer = RetryAnalyzer.class)
    public void verifyThatAllElementsAreVisibleOnResetPasswordPage(){
        loginPage.clickOnForgottenPasswordLink();

        wait.until(ExpectedConditions.urlToBe(resetPasswordURL));
        Assert.assertTrue(resetPasswordPage.resetPasswordTitle.isDisplayed());
        Assert.assertEquals(resetPasswordPage.resetPasswordTitle.getText(), resetTitle);
        Assert.assertTrue(resetPasswordPage.emailField.isDisplayed());
        Assert.assertTrue(resetPasswordPage.sendButton.isDisplayed());

        resetPasswordPage.inputEmail(validEmail);
        resetPasswordPage.clickOnSendButton();

        Assert.assertTrue(resetPasswordPage.goToLoginButton.isDisplayed());
        Assert.assertTrue(resetPasswordPage.alertMessageIsVisible());
    }

    @Test (priority = 40, retryAnalyzer = RetryAnalyzer.class)
    public void verifyThatThePlaceholderTextInAllFieldsOnLoginPageAreVisible(){
        loginPage.emailField.click();
        Assert.assertTrue(loginPage.emailField.getAttribute("placeholder").equalsIgnoreCase(emailPlaceholder));
        loginPage.passwordField.click();
        Assert.assertTrue(loginPage.passwordField.getAttribute("placeholder").equalsIgnoreCase(passwordPlaceholder));
    }

    @Test (priority = 50, retryAnalyzer = RetryAnalyzer.class)
    public void verifyThatThePlaceholderTextInAllFieldsOnResetPasswordPageAreVisible() {
        loginPage.clickOnForgottenPasswordLink();
        resetPasswordPage.emailField.click();
        Assert.assertTrue(resetPasswordPage.emailField.getAttribute("placeholder").equalsIgnoreCase(emailPlaceholder));
    }

    @Test (priority = 60, retryAnalyzer = RetryAnalyzer.class)
    public void verifyThatThePlaceholderTextInAllFieldsOnOtpValidatePageAreVisible(){
        loginPage.inputEmail(validEmail);
        loginPage.inputPassword(validPassword);
        loginPage.clickOnLoginButton();
        otpValidatePage.otpField.click();
        Assert.assertTrue(otpValidatePage.otpField.getAttribute("placeholder").equalsIgnoreCase(otpPlaceholder));
    }

    @AfterMethod
    public void tearDownTest(){
        driver.manage().deleteAllCookies();
        driver.quit();
    }
}
