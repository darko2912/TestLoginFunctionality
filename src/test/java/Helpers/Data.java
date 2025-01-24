package Helpers;

import Base.BaseTest;
import com.github.javafaker.Faker;

public class Data extends BaseTest {

    //Valid credentials for login
    public static final String validEmail = excelReader.getStringData("LoginData",1,0);
    public static final String validPassword = excelReader.getStringData("LoginData", 1,1);

    //Titles
    public static final String loginTitle = "Login to Super Ego Holding";
    public static final String otpTitle = "Check your Email and Enter OTP token";
    public static final String resetTitle = "Reset Password";

    //Messages
    public static final String invalidData = "Wrong email or password, please try again.";
    public static final String alertSuccessMessage = "You have been sent an email with a link to reset your password";
    public static final String requiredMessage = "The email field is required";
    public static final String notFoundUser = "User not found";
    public static final String invalidEmail = "The email field must be a valid email";

    //Very long password
    static Faker faker = new Faker();
    public static String fakerLongPassword(){
        return faker.internet().password(100,120,true,true,true);
    }

    //Placeholders
    public static final String emailPlaceholder = "Email";
    public static final String passwordPlaceholder = "Password";
    public static final String otpPlaceholder = "OTP token";
}
