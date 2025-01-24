package Pages;

import Base.BaseTest;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class ResetPasswordPage extends BaseTest {

    public ResetPasswordPage(){
        PageFactory.initElements(driver, this);
    }

    @FindBy(id = "input-42")
    public WebElement emailField;

    @FindBy(className = "v-toolbar__title")
    public WebElement resetPasswordTitle;

    @FindBy(className = "v-btn__content")
    public WebElement sendButton;

    @FindBy(className = "v-messages__message")
    public WebElement errorMessage;

    @FindBy(className = "v-alert__content")
    public WebElement alertMessage;

    @FindBy(css = ".v-btn.v-btn--router.v-btn--text.theme--light.v-size--default.primary--text")
    public WebElement goToLoginButton;

    //-------------------------------------------

    public void inputEmail(String email){
        emailField.clear();
        emailField.sendKeys(email);
    }

    public void clickOnSendButton(){
        sendButton.click();
    }

    public boolean errorMessageIsVisible(){
        boolean isPresent = false;
        try {
            isPresent = errorMessage.isDisplayed();
        } catch (Exception e) {

        }
        return isPresent;
    }

    public boolean alertMessageIsVisible(){
        boolean isPresent = false;
        try {
            isPresent = alertMessage.isDisplayed();
        } catch (Exception e) {

        }
        return isPresent;
    }

    public void clickOnGoToLoginButton(){
        goToLoginButton.click();
    }

}
