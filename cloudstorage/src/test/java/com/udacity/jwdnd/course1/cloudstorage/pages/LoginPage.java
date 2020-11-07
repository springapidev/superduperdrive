package com.udacity.jwdnd.course1.cloudstorage.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class LoginPage {
    private WebDriver driver;

    @FindBy(name = "username")
    private WebElement userlogin;
    @FindBy(name = "password")
    private WebElement pass;
    @FindBy(id = "gotosignup")
    private WebElement signupLink;
    @FindBy(name = "loginsubmit")
    private WebElement btnSubmit;


    public LoginPage(WebDriver webDriver) {
        driver = webDriver;
        PageFactory.initElements(webDriver, this);
    }

    private void waitForVisibility(WebElement element) throws Error {
        new WebDriverWait(driver, 60)
                .until(ExpectedConditions.visibilityOf(element));
    }

    public void login(String username, String password) {
        userlogin.sendKeys(username);
        pass.sendKeys(password);
        btnSubmit.click();
    }

    public void goToSignUp() {
        waitForVisibility(signupLink);
        signupLink.click();
    }

}