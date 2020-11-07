package com.udacity.jwdnd.course1.cloudstorage.pages;


import com.udacity.jwdnd.course1.cloudstorage.entity.Credentials;
import com.udacity.jwdnd.course1.cloudstorage.entity.Note;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class HomePage {
    private WebDriver driver;

    @FindBy(id = "logout")
    private WebElement logoutButton;

    @FindBy(xpath = "//a[@id='nav-notes-tab']")
    private WebElement notesTab;

    @FindBy(id = "btnshownote")
    private WebElement addNoteModalButton;
    @FindBy(className = "edit")
    private WebElement btnEditNoteModal;
    @FindBy(id = "btnNoteDel")
    private WebElement deleteNoteModalButton;


    @FindBy(id = "note-title")
    private WebElement savenoteTitle;

    @FindBy(id = "note-description")
    private WebElement savenoteDescription;

    @FindBy(id = "noteSubmit")
    private WebElement btnNoteSubmit;

    @FindBy(id = "noteid")
    private WebElement editNoteId;
    @FindBy(id = "notetitle")
    private WebElement EditNoteTitle;

    @FindBy(id = "notedescription")
    private WebElement EditNoteDescription;



    @FindBy(id = "btnNoteEditSubmit")
    private WebElement btnNoteEditSubmit;


    @FindBy(id = "btnNoteDelSubmit")
    private WebElement btnDelNote;

    /**
     * @Note this section is for Open credentials Tab and Models
     */
    @FindBy(xpath = "//a[@id='nav-credentials-tab']")
    private WebElement credentialsTab;

    @FindBy(id = "btnAddNewCredential")
    private WebElement brnAddNewCredentials;

    @FindBy(id = "btnEditCredencialShow")
    private WebElement btnEditCredencialShow;

    @FindBy(id = "btnCredntialDelShow")
    private WebElement btnCredntialDelShow;

    @FindBy(id = "btnCredntialDelSubmit")
    private WebElement btnCredntialDelSubmit;


    /**
     * @Note this section is for save credentials
     */
    @FindBy(id = "credential-url")
    private WebElement saveUrl;

    @FindBy(id = "credential-username")
    private WebElement saveUserName;

    @FindBy(id = "credential-password")
    private WebElement savePassword;

    @FindBy(id = "key")
    private WebElement saveKey;

    @FindBy(id = "secretkey")
    private WebElement saveSecretkey;

    @FindBy(id = "btnCredentialSave")
    private WebElement btnCredentialSave;

    /**
     * @Note this section is for edit credentials
     */
    @FindBy(id = "credentialid")
    private WebElement editCredentialId;

    @FindBy(id = "url")
    private WebElement editUrl;



    @FindBy(id = "username")
    private WebElement editUsername;

    @FindBy(id = "password")
    private WebElement editPass;

    @FindBy(id = "keys")
    private WebElement editKeys;

    @FindBy(id = "secretkeys")
    private WebElement editSecretKeys;




    public HomePage(WebDriver webDriver) {
        driver = webDriver;
        PageFactory.initElements(webDriver, this);
    }

    private void waitForVisibility(WebElement element) throws Error {
        new WebDriverWait(driver, 40)
                .until(ExpectedConditions.visibilityOf(element));
    }


    public void logout() {
        logoutButton.click();
    }


    public void goToNotesTab() {
        waitForVisibility(notesTab);
        notesTab.click();
    }

    public void goToCredentialsTab() {
        waitForVisibility(credentialsTab);
        credentialsTab.click();
    }


    public void addNewNote(String noteTitle, String noteDescription) {
        goToNotesTab();
        waitForVisibility(addNoteModalButton);
        addNoteModalButton.click();
        saveNote(noteTitle, noteDescription);
    }

    private void saveNote(String noteTitle, String noteDescription) {
        waitForVisibility(savenoteTitle);
        this.savenoteTitle.sendKeys(noteTitle);
        this.savenoteDescription.sendKeys(noteDescription);
        waitForVisibility(btnNoteSubmit);
        this.btnNoteSubmit.click();
    }


    public Note getFirstNote() {
        waitForVisibility(savenoteTitle);
        Note result = new Note();
        result.setNotetitle(savenoteTitle.getText());
        result.setNotedescription(savenoteDescription.getText());
        return result;
    }

    public void editFirstNote(String noteTitle, String noteDescription) {
        goToNotesTab();
        waitForVisibility(btnEditNoteModal);
        btnEditNoteModal.click();
        clearNoteInputs();
        saveNote(noteTitle, noteDescription);
    }

    public void clearNoteInputs() {
        waitForVisibility(savenoteTitle);
        this.savenoteTitle.clear();
        this.savenoteDescription.clear();
    }

    public boolean isNoteTitleDisplayed() {
        return isElementPresent(By.id("note-title-row"));
    }

    public boolean isNoteDescriptionDisplayed() {
        return isElementPresent(By.id("note-description-row"));
    }

    public boolean isElementPresent(By by) {
        try {
            driver.findElement(by);
            return true;
        } catch (org.openqa.selenium.NoSuchElementException e) {
            return false;
        }
    }

    public void deleteFirstNote() {
        goToNotesTab();
        waitForVisibility(deleteNoteModalButton);
        deleteNoteModalButton.click();
        waitForVisibility(btnDelNote);
        btnDelNote.click();
    }

    public void addNewCredential(String url, String username, String password, String key, String screctKey) {
        goToCredentialsTab();
        waitForVisibility(btnCredentialSave);
        btnCredentialSave.click();
        saveCredential(url, username, password,key,screctKey);
    }


    public void saveCredential(String url, String username, String password, String key, String screctKey) {
        waitForVisibility(saveUrl);
        this.saveUrl.sendKeys(url);
        this.saveUserName.sendKeys(username);
        this.savePassword.sendKeys(password);
        this.saveKey.sendKeys(key);
        this.saveSecretkey.sendKeys(screctKey);
        waitForVisibility(btnCredentialSave);
        this.btnCredentialSave.click();
    }
    public void editCredential(String url, String username, String password) {
        waitForVisibility(saveUrl);
        this.saveUrl.sendKeys(url);
        this.saveUserName.sendKeys(username);
        this.savePassword.sendKeys(password);
        waitForVisibility(btnCredentialSave);
        this.btnCredentialSave.click();
    }

    public Credentials getFirstCredential() {
        waitForVisibility(saveUrl);
        Credentials result = new Credentials();
        result.setUrl(saveUrl.getText());
        result.setUsername(savePassword.getText());
        result.setPassword(savePassword.getText());
        result.setKey(saveKey.getText());
        result.setSecretkey(saveSecretkey.getText());
        return result;
    }

    public void goToEditButton() {
        waitForVisibility(btnEditCredencialShow);
        btnEditCredencialShow.click();
    }

    public void editFirstCredential(String url, String username, String password) {
        clearCredentialInput();
        editCredential(url, username, password);
    }

    public void clearCredentialInput() {
        waitForVisibility(saveUrl);
        this.saveUrl.clear();
        this.saveUserName.clear();
        this.savePassword.clear();
    }

    public Credentials getCredentialModalInput() {
        goToEditButton();
        waitForVisibility(btnEditCredencialShow);
        Credentials result = new Credentials();
        result.setUrl(this.editUrl.getAttribute("value"));
        result.setUsername(this.editUsername.getAttribute("value"));
        result.setPassword(this.editPass.getAttribute("value"));
        return result;
    }


    public void deleteFirstCredential() {
        goToCredentialsTab();
        waitForVisibility(btnCredntialDelShow);
        btnCredntialDelShow.click();
        waitForVisibility(btnCredntialDelSubmit);
        btnCredntialDelSubmit.click();
    }

    public boolean isCredentialUrlDisplayed() {
        return isElementPresent(By.id("credential-url-row"));
    }

    public boolean isCredentialUsernameDisplayed() {
        return isElementPresent(By.id("credential-username-row"));
    }


}