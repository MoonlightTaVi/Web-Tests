package com.github.tavi.rep_portal.util.v1;

import java.time.Duration;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.github.tavi.rep_portal.util.IDashboardPage;

public class DashboardPage implements IDashboardPage {
	
	private WebDriverWait wait;
	
	@FindBy(xpath= "//button[contains(@class, 'ghostButton') and .//span[contains(text(), 'Add new widget')]]")
	private WebElement btdAddNewWidget;
	@FindBy(xpath= "//div[text()='Passing rate summary']")
	private WebElement inradWidgetType;
	@FindBy(xpath= "//button[contains(@class, 'ghostButton') and .//span[contains(text(), 'Next step')]]")
	private WebElement btnNextStep;
	@FindBy(xpath= "//span[contains(@class, 'inputRadio')]")
	private WebElement inradFilter;
	@FindBy(xpath= "//input[@placeholder='Enter widget name']")
	private WebElement txaWidgetName;
	@FindBy(xpath= "//button[contains(@class, 'bigButton') and contains(text(), 'Add')]")
	private WebElement btnAdd;

	
	public DashboardPage(WebDriver driver) {
		wait = new WebDriverWait(driver, Duration.ofSeconds(15));
		PageFactory.initElements(driver, this);
	}
	
	
	@Override
	public IDashboardPage startAddingNewWidget() {
		wait.until(ExpectedConditions.elementToBeClickable(
				btdAddNewWidget
				));
		btdAddNewWidget.click();
		return this;
	}

	@Override
	public IDashboardPage chooseWidgetType() {
		wait.until(ExpectedConditions.elementToBeClickable(
				inradWidgetType
				));
		inradWidgetType.click();
		return this;
	}

	@Override
	public IDashboardPage clickNextStepButton() {
		wait.until(ExpectedConditions.elementToBeClickable(
				btnNextStep
				));
		btnNextStep.click();
		return this;
	}
	
	@Override
	public IDashboardPage chooseFilter() {
		wait.until(ExpectedConditions.elementToBeClickable(
				inradFilter
				));
		inradFilter.click();
		return this;
	}

	@Override
	public IDashboardPage enterWidgetName(String widgetName) {
        wait.until(
        		ExpectedConditions.visibilityOf(
        				txaWidgetName
        				)
        		);
        txaWidgetName.clear();
        txaWidgetName.sendKeys(widgetName);
		return this;
	}

	@Override
	public void finishAddingNewWidget() {
		wait.until(ExpectedConditions.elementToBeClickable(
				btnAdd
				));
		btnAdd.click();
	}

}
