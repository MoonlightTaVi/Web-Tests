package com.github.tavi.rep_portal.util.v1;

import java.time.Duration;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.github.tavi.rep_portal.util.ILoginPage;

public class LoginPage implements ILoginPage {
	
	private WebDriverWait wait;
	
	@FindBy(css= "input[type='text']")
	private WebElement txaUsername;
	@FindBy(css= "input[type='password']")
	private WebElement txaPassword;
	@FindBy(css= "button[type='submit']")
	private WebElement btnSubmit;
	
	
	public LoginPage(WebDriver driver) {
		wait = new WebDriverWait(driver, Duration.ofSeconds(15));
		PageFactory.initElements(driver, this);
	}

	@Override
	public void login(String username, String password) {
		wait.until(
				ExpectedConditions.visibilityOf(txaUsername)
				);
		txaUsername.clear();
		txaUsername.sendKeys(username);
		
		wait.until(
				ExpectedConditions.visibilityOf(txaPassword)
				);
		txaPassword.clear();
		txaPassword.sendKeys(password);
		
		btnSubmit.click();
	}

}
