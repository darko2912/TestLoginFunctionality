package Pages;

import Base.BaseTest;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class OtpValidatePage extends BaseTest {

    public OtpValidatePage(){
        PageFactory.initElements(driver, this);
    }

    @FindBy(className = "v-toolbar__title")
    public WebElement otpValidateTitle;

    @FindBy(id = "input-47")
    public WebElement otpField;

    @FindBy(className = "v-btn__content")
    public WebElement verifyButton;

    @FindBy(css = ".v-btn.v-btn--text.theme--light.v-size--default.primary--text")
    public WebElement resendOtpButton;

    //---------------------------------------------

}
