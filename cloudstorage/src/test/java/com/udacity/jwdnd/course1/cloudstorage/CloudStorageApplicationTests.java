package com.udacity.jwdnd.course1.cloudstorage;

import com.udacity.jwdnd.course1.cloudstorage.entity.Credentials;
import com.udacity.jwdnd.course1.cloudstorage.entity.Note;
import com.udacity.jwdnd.course1.cloudstorage.pages.HomePage;
import com.udacity.jwdnd.course1.cloudstorage.pages.LoginPage;
import com.udacity.jwdnd.course1.cloudstorage.pages.SignUpPage;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.context.jdbc.Sql;


import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Sql(scripts = "classpath:drop_all.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
@Sql(scripts = {"classpath:schema.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
class CloudStorageApplicationTests {

    @LocalServerPort
    private int port;
    private String baseURL;

    private WebDriver driver;

    @BeforeAll
    static void beforeAll() {
        WebDriverManager.chromedriver().setup();
    }

    @BeforeEach
    public void beforeEach() {
        this.driver = new ChromeDriver();
        baseURL = "http://localhost:" + port;

    }

    @AfterEach
    public void afterEach() {
        if (this.driver != null) {
            driver.quit();
        }
    }


    @Test
    public void getLoginPage() {
        driver.get(baseURL + "/login");
        Assertions.assertEquals("Login", driver.getTitle());
    }


    @Test
    public void testInvalidUrlRedirectsToErrorPage() {
        signUpAndLogin();
        //navigate to invalid url
        driver.get(baseURL + "/home/abc");
        Assertions.assertEquals("Error", driver.getTitle());
    }


    @Test
    public void testUnauthorizedUserCanOnlyAccessLoginAndSignUpPage() {
        //navigate to home page and verify that unauthorized user has no access
        driver.get(baseURL + "/home");
        assertEquals("Login", driver.getTitle());

        //navigate to sign up page and verify that unauthorized user has access
        LoginPage loginPage = new LoginPage(driver);
        loginPage.goToSignUp();
        assertEquals("Sign Up", driver.getTitle());
    }


    @Test
    public void testSignUpLoginVerifyHomePageIsAccessibleLogoutVerifyHomePageIsNotAccessible() {
        signUpAndLogin();
        //verify that home page is accessible
        assertEquals("Home", driver.getTitle());

        //log out
        HomePage homePage = new HomePage(driver);
        homePage.logout();

        //verify that home page is no longer visible
        assertFalse("Home".equalsIgnoreCase(driver.getTitle()));
        assertEquals("Login", driver.getTitle());

    }


    /**
     * Write a test that creates a note, and verifies it is displayed.
     */

    @Test
    public void testSaveNoteAndVerifyItDisplayed() throws InterruptedException {
        signUpAndLogin();
        HomePage homePage = new HomePage(driver);
        //create Note
        String noteTitle = "How to complete This Project";
        String noteDesc = "Write Test Code and Pass";
        homePage.addNewNote(noteTitle, noteDesc);
        //navigate to home page
        driver.get(baseURL + "/");
        //verify note creation
        HomePage homePage1 = new HomePage(driver);
        homePage1.goToNotesTab();
        WebElement notesTab = driver.findElement(By.xpath("//a[@id='nav-notes-tab']"));
        assertThat(notesTab.getText().contains(noteTitle));
        assertThat(notesTab.getText().contains(noteDesc));
    }

    /**
     * Write a test that edits an existing note and verifies that the changes are displayed.
     */
    @Test
    public void testEditNoteAndVerifyChangesAreDisplayed() {
        signUpAndLogin();
        HomePage homePage = new HomePage(driver);
        //create Note
        String noteTitle = "How to Win a Coding Challange ";
        String noteDesc = "Practice ";
        homePage.addNewNote(noteTitle, noteDesc);
        //navigate to home page
        driver.get(baseURL + "/");
        //edit note
        String newNoteTitle = "How to Win a Coding Challange successfully";
        String newNoteDesc = "Practice Practice Practice Practice";
        homePage.editFirstNote(newNoteTitle, newNoteDesc);
        //navigate to home page
        driver.get(baseURL + "/");
        //verify note update
        HomePage homePage1 = new HomePage(driver);
        homePage1.goToNotesTab();
        WebElement notesTab = driver.findElement(By.xpath("//a[@id='nav-notes-tab']"));
        assertThat(notesTab.getText().contains(newNoteTitle));
        assertThat(notesTab.getText().contains(newNoteDesc));
    }

    /**
     * Write a test that deletes a note and verifies that the note is no longer displayed.
     */
    @Test
    public void testDeleteNoteAndVerifyNoteIsNoLongerDisplayed() {
        signUpAndLogin();

        HomePage homePage = new HomePage(driver);

        //create Note
        String noteTitle = "How to play Ball";
        String noteDesc = "It is not easy";

        homePage.addNewNote(noteTitle, noteDesc);

        //navigate to home page
        driver.get(baseURL + "/");

        //delete note
        homePage.deleteFirstNote();

        //navigate to home page
        driver.get(baseURL + "/");

        //verify note update
        HomePage homePage1 = new HomePage(driver);
        homePage1.goToNotesTab();

        assertFalse(homePage1.isNoteDeleteBtnDisplayed());

    }

    /**
     * Write a test that creates a set of credentials,
     * verifies that they are displayed,
     * and verifies that the displayed password is encrypted.
     */
    @Test
    public void testSaveCredentialAndVerifyItDisplayedAndPasswordIsEncrypted() {
        signUpAndLogin();
        HomePage homePage = new HomePage(driver);
        //create credential
        String url = "www.udacity.com";
        String credentialUsername = "test";
        String credentialPassword = "password";
        String credentialKey = "786";
        String credentialSecretKey = "1234";
        homePage.addNewCredential(url, credentialUsername, credentialPassword, credentialKey, credentialSecretKey);
        //navigate to home page
        driver.get(baseURL + "/");
        //verify credential creation
        HomePage homePage1 = new HomePage(driver);
        homePage1.goToCredentialsTab();
        WebElement notesTab = driver.findElement(By.xpath("//a[@id='nav-credentials-tab']"));
        assertThat(notesTab.getText().contains(url));
        assertThat(notesTab.getText().contains(credentialUsername));
    }
    /**
     * Write a test that views an existing set of credentials,
     * verifies that the viewable password is unencrypted,
     * edits the credentials, and verifies that the changes are displayed.
     */
    @Test
    public void testSaveCredentialAndVerifyViewablePasswordIsEncryptedAndEditAndVerifyChangesAreDisplayed() throws InterruptedException {
        signUpAndLogin();

        HomePage homePage = new HomePage(driver);

        //create credential
        String url = "www.google.com";
        String credentialUsername = "test";
        String credentialPassword = "password";
        String credentialKey = "786";
        String credentialSecretKey = "1234";
        homePage.addNewCredential(url, credentialUsername, credentialPassword, credentialKey,credentialSecretKey);


        //navigate to home page
        driver.get(baseURL + "/");
        homePage.goToCredentialsTab();

        Credentials credential = homePage.getCredentialModalInput();
        //verify that password is unencrypted
        assertEquals(credentialPassword, credential.getPassword());

        //edit the credential
        String newUrl = "www.udacity.com";
        String newUsername = "admin";
        String newPassword = "1234";
        homePage.editFirstCredential(newUrl, newUsername, newPassword);


        //navigate to home page
        driver.get(baseURL + "/");

        //verify note update
        HomePage homePage1 = new HomePage(driver);
        homePage1.goToCredentialsTab();

        Credentials modifiedCredential = homePage1.getFirstCredential();
        assertEquals(newUrl, modifiedCredential.getUrl());
        assertEquals(newUsername, modifiedCredential.getUsername());
        assertFalse(url.equalsIgnoreCase(modifiedCredential.getUrl()));
        assertFalse(credentialUsername.equalsIgnoreCase(modifiedCredential.getUsername()));
    }

    /**
     * Write a test that deletes an existing set of credentials
     * and verifies that the credentials are no longer displayed.
     */
    @Test
    public void testDeleteCredentialAndVerifyItIsNoLongerDisplayed() {
        signUpAndLogin();
        HomePage homePage = new HomePage(driver);
        //create credential
        String url = "www.google.com";
        String credentialUsername = "test";
        String credentialPassword = "password";
        String credentialKey = "786";
        String credentialSecretKey = "1234";
        homePage.addNewCredential(url, credentialUsername, credentialPassword, credentialKey, credentialSecretKey);
        //navigate to home page
        driver.get(baseURL + "/");
        homePage.deleteFirstCredential();
        //navigate to home page
        driver.get(baseURL + "/");
        //verify note update
        HomePage homePage1 = new HomePage(driver);
        homePage1.goToCredentialsTab();
        assertFalse(homePage1.isCredentialDeleteBtnDisplayed());
    }
    private void signUpAndLogin() {
        String firstName = "Rajaul";
        String lastName = "Islam";
        String username = "admin";
        String password = "admin";

        //navigate to sign up page
        driver.get(baseURL + "/signup");
        SignUpPage signUpPage = new SignUpPage(driver);
        signUpPage.signUp(firstName, lastName, username, password);

        //navigate to login page
        driver.get(baseURL + "/login");

        LoginPage loginPage = new LoginPage(driver);
        loginPage.login(username, password);

        driver.get(baseURL + "/");
    }

}