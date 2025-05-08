package com.github.tavi.rep_portal;

import java.time.Duration;
import java.util.*;

import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.*;

import com.github.tavi.rep_portal.util.*;
import com.github.tavi.rep_portal.util.v1.*;


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
        ILoginPage login = new LoginPage(driver);
        login.login(USER, PASSWORD);

        // Navigate to dashboard
        wait.until(ExpectedConditions.urlContains("/ui/#default_personal/launches/all"));
        driver.get(BASE_URL + "/ui/#default_personal/dashboard/17");

        IDashboardPage dashboard = new DashboardPage(driver)
        		.startAddingNewWidget()
        		.chooseWidgetType()
        		.clickNextStepButton()
        		.chooseFilter()
        		.clickNextStepButton()
        		.enterWidgetName("Task Progress Widget " + browser.toString());
        dashboard.finishAddingNewWidget();
        
        // Assert
        WebElement widgetHeader = wait.until(
        		ExpectedConditions.visibilityOfElementLocated(
		            By.xpath(
		            		"//div[contains(@class, 'widgetHeader') and contains(text(), 'Task Progress Widget "
		            				+ browser.toString() + "')]"
		            	)
		           )
        );
        Assertions.assertTrue(widgetHeader.isDisplayed(), "Widget was not added successfully");
    }
    
    private void initializeDriver(Browser browser) {
        switch (browser) {
            case CHROME:
            	// Chrome complains about credentials leak, making test fail
            	//  So disable detection
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
