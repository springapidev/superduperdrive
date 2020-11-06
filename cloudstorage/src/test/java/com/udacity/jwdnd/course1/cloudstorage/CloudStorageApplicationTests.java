package com.udacity.jwdnd.course1.cloudstorage;


import com.udacity.jwdnd.course1.cloudstorage.services.UserService;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.web.client.HttpClientErrorException;

import java.io.IOException;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
class CloudStorageApplicationTests {

    @LocalServerPort
    private int port;

    private WebDriver driver;

    @Autowired
    private UserService userService;

    @BeforeAll
    static void beforeAll() {
        WebDriverManager.chromedriver().setup();
    }

    @BeforeEach
    public void beforeEach() {
        this.driver = new ChromeDriver();
    }

    @AfterEach
    public void afterEach() throws IOException {
        if (this.driver != null) {
            driver.quit();
        }
    }

    //	@Test
//	public void getLoginPage() {
//		driver.get("http://localhost:" + this.port + "/login");
//		Assertions.assertEquals("Login", driver.getTitle());
//	}
    //1. Write Tests for User Signup, Login, and Unauthorized Access Restrictions.
    @Test
    public void signup() {
        driver.get("http://localhost:8080/signup");
        //find firstname, lastname, username and password
        WebElement firstname = driver.findElement(By.name("firstName"));
        WebElement lastname = driver.findElement(By.name("lastName"));
        WebElement username = driver.findElement(By.name("username"));
        WebElement password = driver.findElement(By.name("password"));
        //set values for firstname, lastname, username and password
        firstname.sendKeys("Md. Rajaul");
        lastname.sendKeys("Islam");
        username.sendKeys("admin");
        password.sendKeys("admin");
        driver.findElement(By.name("submit")).click();

    }

    @Test
    public void login() {
        driver.get("http://localhost:8080/login");
        //find username and password

        WebElement username = driver.findElement(By.name("username"));
        WebElement password = driver.findElement(By.name("password"));
        //set values for username and password
        username.sendKeys("admin");
        password.sendKeys("admin");

        driver.findElement(By.name("loginsubmit")).click();
        String expectedPageTitle = "Home";
        String actuatPageTitle = driver.getTitle();
        if (expectedPageTitle.equalsIgnoreCase(actuatPageTitle)) {
            System.out.println("successfully loggedIn");
            try {
                Thread.sleep(3000);
                driver.findElement(By.id("logout")).click();

            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("Login Failed");
        }
    }

    @Test
    public void verifyUnauthorizedAccessRestrictions() {

        try {
            driver.get("http://localhost:8080/");
        } catch (HttpClientErrorException.Unauthorized r) {
            driver.get("http://localhost:8080/signup");
            driver.get("http://localhost:8080/login");
        }

    }

    //2. Write Tests for Note Creation, Viewing, Editing, and Deletion.
//    @Test
//    public void createNoteAndVerifyItsViewing() {
//        loginAvoidRepeat();
//
//            driver.get("http://localhost:8080/");
//            driver.findElement(By.id("nav-notes-tab")).click();
//            driver.findElement(By.id("btnshownote")).click();
//            WebElement notetitle = driver.findElement(By.name("notetitle"));
//            notetitle.sendKeys("Note Title");
//            WebElement notedescription = driver.findElement(By.name("notedescription"));
//            notedescription.sendKeys("Note Description");
//            driver.findElement(By.id("notesave")).click();
//
//    }


    public void loginAvoidRepeat() {
        driver.get("http://localhost:8080/login");
        WebElement username = driver.findElement(By.name("username"));
        WebElement password = driver.findElement(By.name("password"));
        username.sendKeys("admin");
        password.sendKeys("admin");
        driver.findElement(By.name("loginsubmit")).click();

    }
}
