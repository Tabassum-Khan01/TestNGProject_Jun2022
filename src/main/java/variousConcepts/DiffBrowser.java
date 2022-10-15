//open techfios billing website in 2 diff browser
package variousConcepts;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

public class DiffBrowser {

	WebDriver driver; 
	String browser = "Chrome";
	
	// Element list
	By userNameField = By.xpath("//input[@id='username']");
	By passwordField = By.xpath("//input[@id='password']");
	By signInButtonField = By.xpath("//button[@type='submit']");
	By dashboardHeaderField = By.xpath("//h2[contains(text(), 'Dashboard')]");
	// By CUSTOMER = By.xpath("//i[@class = 'fa fa-tachometer']");
	By customerMenuField = By.xpath("//*[@id=\"side-menu\"]/li[3]/a/span[1]");
	By addCustomerField = By.xpath("//*[@id=\"side-menu\"]/li[3]/ul/li[1]/a");
	By fullNameField = By.xpath("//*[@id=\"account\"]");
	By companyDropDownFieldField = By.xpath("//select[@id='cid']");

	@BeforeTest
	public void launchBrowser() {
		
		if (browser.equalsIgnoreCase("chrome")) {
			 System.setProperty("webdriver.chrome.driver", "driver/chromedriver.exe");
			 driver = new ChromeDriver();
			
		} else if (browser.equalsIgnoreCase("firefox")){
			System.setProperty("webdriver.edge.driver", "driver/msedgedriver.exe");
			driver = new EdgeDriver();
			
		}
		driver.manage().deleteAllCookies();
		driver.get("https://techfios.com/billing/?ng=login/");
		// driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);

	}

	@Test
	public void loginTest() {

		driver.findElement(userNameField).sendKeys("demo@techfios.com");
		driver.findElement(passwordField).sendKeys("abc123");
		driver.findElement(signInButtonField).click();

		Assert.assertEquals(driver.findElement(dashboardHeaderField).getText(), "Dahsboard", "header does not match");
		Assert.assertEquals(driver.getTitle(), "Dashboard- iBilling", "title does not match");
		Assert.assertTrue(driver.findElement(dashboardHeaderField).isDisplayed(), "Header does not appear");
	}

	public void tearDown() {
		driver.close();
		driver.quit();
	}

}
