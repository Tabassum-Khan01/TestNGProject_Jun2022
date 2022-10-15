//read browser options from properties file

package variousConcepts;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class InpuStreamExmpl {

	WebDriver driver;
	String browser;
	String url;

	// Element list
	By userNameField = By.xpath("//input[@id='username']");
	By passwordField = By.xpath("//input[@id='password']");
	By signInButtonField = By.xpath("//button[@type='submit']");
	By dashboardHeaderField = By.xpath("//h2[contains(text(), 'Dashboard')]");
	By customerMenuField = By.xpath("//*[@id=\"side-menu\"]/li[3]/a/span[1]");
	// By addCustomerMenuField = By.xpath(xpathExpression);
	By addCustomerHeader = By.xpath("//h5[text()='Add Contact']");
	By addCustomerField = By.xpath("//*[@id=\"side-menu\"]/li[3]/ul/li[1]/a");
	By fullNameField = By.xpath("//input[@id=\"account\"]");
	By companyDropDownFieldField = By.xpath("//select[@id='cid']");
	By emailField = By.xpath("//input[@id ='email']");
	By countryDropDownField = By.xpath("//select[@id='country']");

	@BeforeClass
	public void readConfig() {
		// InputStream //BufferedReader //FileReader //Scanner

		try {
			InputStream input = new FileInputStream("src/main/java/config/config.properties");
			Properties prop = new Properties();
			prop.load(input);
			browser = prop.getProperty("browser");
			url = prop.getProperty("url");

		} catch (IOException e) {

		}

	}

	@BeforeMethod
	public void launchBrowser() {

		if (browser.equalsIgnoreCase("chrome")) {
			System.setProperty("webdriver.chrome.driver", "driver/chromedriver.exe");
			driver = new ChromeDriver();

		} else if (browser.equalsIgnoreCase("edge")) {
			System.setProperty("webdriver.edge.driver", "driver/msedgedriver.exe");
			driver = new EdgeDriver();

		}
		driver.manage().deleteAllCookies();
		driver.get(url);
		// driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);

	}

	// @Test(priority = 1)
	public void loginTest() {

		driver.findElement(userNameField).sendKeys("demo@techfios.com");
		driver.findElement(passwordField).sendKeys("abc123");
		driver.findElement(signInButtonField).click();

		Assert.assertEquals(driver.findElement(dashboardHeaderField).getText(), "Dashboard", "header does not match");
		Assert.assertEquals(driver.getTitle(), "Dashboard- iBilling", "Title does not match");
		Assert.assertTrue(driver.findElement(dashboardHeaderField).isDisplayed(), "Header does not appear");
	}

	@Test (priority = 2)
	public void addCustomer() throws InterruptedException {
		loginTest();
		driver.findElement(customerMenuField).click();
		driver.findElement(addCustomerField).click();

		// WebDriverWait wait = new WebDriverWait(driver,30);
		// wait.until(ExpectedConditions.visibilityOfElementLocated(addCustomerHeader));

		Thread.sleep(3000);
		Assert.assertEquals(driver.findElement(addCustomerHeader).getText(), "Add Contact",
				"Contact Header not found.");

		int a = generateRandomNum(999);
		driver.findElement(fullNameField).sendKeys("Dua Lipa" + a);

		selectFromDropdown(driver.findElement(companyDropDownFieldField), "Apple");

		driver.findElement(emailField).sendKeys(a + "abc123@techfios.com");

		selectFromDropdown(countryDropDownField, "Uzbekistan");

	}

	private int generateRandomNum(int boundaryNum) {
		Random rnd = new Random();
		int genNum = rnd.nextInt(999);
		return genNum;

	}

	// follwong same name method w/ diff arguments
	private void selectFromDropdown(By locator, String visibleText) {
		Select sel = new Select(driver.findElement(locator));
		sel.selectByVisibleText(visibleText);

	}

	private void selectFromDropdown(WebElement element, String visibleText) {

		Select sel = new Select(element);
		sel.selectByVisibleText(visibleText);

	}

	// public void tearDown() {
	// driver.close();
	// driver.quit();
	// }

}
