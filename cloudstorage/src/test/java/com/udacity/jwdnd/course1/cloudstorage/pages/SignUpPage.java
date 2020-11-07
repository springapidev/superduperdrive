package com.udacity.jwdnd.course1.cloudstorage.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class SignUpPage {
    @FindBy(name = "firstName")
    private WebElement fname;
    @FindBy(name = "lastName")
    private WebElement lname;
    @FindBy(name = "username")
    private WebElement uname;
    @FindBy(name = "password")
    private WebElement pass;
    @FindBy(id = "loginlink")
    private WebElement loginLink;
    @FindBy(name = "submit")
    private WebElement btnSubmit;


    public SignUpPage(WebDriver webDriver) {
        PageFactory.initElements(webDriver, this);
    }

    public void signUp(String firstName, String lastName, String username, String password) {
        fname.sendKeys(firstName);
        lname.sendKeys(lastName);
        uname.sendKeys(username);
        pass.sendKeys(password);
        btnSubmit.click();
    }


    public void goToLoginPage() {
        loginLink.click();
    }

}
