package com.github.tavi.rep_portal;

import java.time.Duration;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;


public class WidgetTest {

    private WebDriver driver;
    private static String BASE_URL;
    private static String USER;
    private static String PASSWORD;
    
    private enum Browser {
        CHROME, FIREFOX, EDGE
    }
    
    
    @BeforeAll
    public static void setup() {
    	ResourceBundle rb = ResourceBundle.getBundle("report-portal");
    	BASE_URL = rb.getString("BASE_URL");
    	USER = rb.getString("USER");
    	PASSWORD = rb.getString("PASSWORD");
    }

    @AfterEach
    void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
    
    @ParameterizedTest
    @EnumSource(Browser.class)
    void shouldAddTaskProgressWidget(Browser browser) {
        // Arrange
        initializeDriver(browser);
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));

        // Authenticate
        driver.get(BASE_URL + "/ui/#login");
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("input[type='text']"))).clear();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("input[type='text']"))).sendKeys(USER);
        driver.findElement(By.cssSelector("input[type='password']")).clear();
        driver.findElement(By.cssSelector("input[type='password']")).sendKeys(PASSWORD);
        driver.findElement(By.cssSelector("button[type='submit']")).click();

        // Navigate to dashboard
        wait.until(ExpectedConditions.urlContains("/ui/#default_personal/launches/all"));
        driver.get(BASE_URL + "/ui/#default_personal/dashboard/14");

        wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//button[contains(@class, 'ghostButton') and .//span[contains(text(), 'Add new widget')]]"))).click();
        wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//div[text()='Passing rate summary']"))).click();
        wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//button[contains(@class, 'ghostButton') and .//span[contains(text(), 'Next step')]]"))).click();
        wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//span[contains(@class, 'inputRadio')]"))).click();
        wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//button[contains(@class, 'ghostButton') and .//span[contains(text(), 'Next step')]]"))).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[@placeholder='Enter widget name']"))).clear();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[@placeholder='Enter widget name']"))).sendKeys("Task Progress Widget " + browser.toString());
        wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//button[contains(@class, 'bigButton') and contains(text(), 'Add')]"))).click();
        
        WebElement widgetHeader = wait.until(ExpectedConditions.visibilityOfElementLocated(
            By.xpath("//div[contains(@class, 'widgetHeader') and contains(text(), 'Task Progress Widget " + browser.toString() + "')]"))
        );
        Assertions.assertTrue(widgetHeader.isDisplayed(), "Widget was not added successfully");
    }
    
    private void initializeDriver(Browser browser) {
        switch (browser) {
            case CHROME:
            	// Chrome complains about credentials leak, making test fail
            	ChromeOptions chromeOptions = new ChromeOptions();
                Map<String, Object> prefs = new HashMap<>();
                prefs.put("profile.password_manager_leak_detection", false);
                chromeOptions.setExperimentalOption("prefs", prefs);
                driver = new ChromeDriver(chromeOptions);
                break;
            case FIREFOX:
                driver = new FirefoxDriver();
                break;
            case EDGE:
                driver = new EdgeDriver();
                break;
            default:
                throw new IllegalArgumentException("Unsupported browser");
        }
        driver.manage().window().maximize();
    }
	
}
