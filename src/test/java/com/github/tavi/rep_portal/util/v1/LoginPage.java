package com.github.tavi.rep_portal.util.v1;

import java.time.Duration;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.github.tavi.rep_portal.util.ILoginPage;

import io.qameta.allure.Step;

public class LoginPage implements ILoginPage {
	
	private WebDriverWait wait;
	
	@FindBy(css= "input[type='text']")
	private WebElement txaUsername;
	@FindBy(css= "input[type='password']")
	private WebElement txaPassword;
	@FindBy(css= "button[type='submit']")
	private WebElement btnSubmit;
	@FindBy(xpath= "//a[contains(@class, 'sidebarButton') and contains(@href, '/dashboard')]")
	private WebElement sbtnDashboards;
	@FindBy(xpath= "//a[contains(@class, 'gridCell') and text()='DEMO DASHBOARD']")
	private WebElement cellDefaultDashboard;
	
	
	public LoginPage(WebDriver driver) {
		wait = new WebDriverWait(driver, Duration.ofSeconds(15));
		PageFactory.initElements(driver, this);
	}

	@Override
	@Step("Log-in to demo.reportportal.io")
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

	@Override
	public void navigateToDashboards() {
		wait.until(ExpectedConditions.elementToBeClickable(
				sbtnDashboards
				));
		sbtnDashboards.click();
	}

	@Override
	public void openDemoDashboard() {
		wait.until(ExpectedConditions.elementToBeClickable(
				cellDefaultDashboard
				));
		cellDefaultDashboard.click();
	}

}
