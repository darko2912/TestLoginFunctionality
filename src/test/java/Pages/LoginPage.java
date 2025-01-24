package Pages;

import Base.BaseTest;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class LoginPage extends BaseTest {

    public LoginPage(){
        PageFactory.initElements(driver,this);
    }

    @FindBy(id = "input-21")
    public WebElement emailField;

    @FindBy(id = "password")
    public WebElement passwordField;

    @FindBy(css = ".v-btn.v-btn--is-elevated.v-btn--has-bg.theme--light.v-size--default.primary")
    public WebElement loginButton;

    @FindBy(css = ".v-btn.v-btn--router.v-btn--text.theme--light.v-size--default.primary--text")
    public WebElement forgottenPasswordLink;

    @FindBy(css = "button[aria-label='append icon']")
    public WebElement showHiddenButton;

    @FindBy(className = "v-alert__wrapper")
    public WebElement errorMessage;

    @FindBy(className = "v-alert__content")
    public WebElement contentErrorMessage;

    @FindBy(className = "v-toolbar__title")
    public WebElement loginPageTitle;

    //--------------------------------------------------

    public void inputEmail(String email){
        emailField.clear();
        emailField.sendKeys(email);
    }

    public void inputPassword(String password){
        passwordField.clear();
        passwordField.sendKeys(password);
    }

    public void clickOnLoginButton(){
        loginButton.click();
    }

    public void clickOnForgottenPasswordLink(){
        forgottenPasswordLink.click();
    }

    public void clickOnShowHiddenButton(){
        showHiddenButton.click();
    }

    public boolean errorMessageIsVisible(){
        boolean isPresent = false;
        try {
            isPresent = errorMessage.isDisplayed();
        } catch (Exception e) {

        }
        return isPresent;
    }



}
