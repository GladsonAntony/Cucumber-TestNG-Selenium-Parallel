/**
 * 
 */
package controllers;

import cucumber.api.java.Before;
import org.openqa.selenium.NotFoundException;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;

import io.github.bonigarcia.wdm.ChromeDriverManager;
import io.github.bonigarcia.wdm.EdgeDriverManager;
import io.github.bonigarcia.wdm.FirefoxDriverManager;
import io.github.bonigarcia.wdm.InternetExplorerDriverManager;
import io.github.bonigarcia.wdm.PhantomJsDriverManager;

/**
 * @Author Gladson Antony
 * @Date 08-Feb-2017
 */
public class WebDriverFactory extends BrowserFactory
{
	public static ThreadLocal<WebDriver> wd = new ThreadLocal<WebDriver>();
	public static String browser;
	public static String url;


	@Before
	//@BeforeTest(alwaysRun=true)
	public void suiteSetup() throws Exception
	{
		System.out.println("Browser: "+Browser);
		System.out.println("WebsiteURL: "+WebsiteURL);
		switch(Browser.toLowerCase())
		{
		case "chrome":
		case "chrome_headless":
		case "opera":
			ChromeDriverManager.getInstance().setup();
			break;

		case  "firefox":
			FirefoxDriverManager.getInstance().setup();
			break;

		case  "ie":
		case "internet explorer":
			InternetExplorerDriverManager.getInstance().setup();
			break;	

		case  "edge":
			EdgeDriverManager.getInstance().setup();
			break;

		case "ghost":
		case "phantom":
			PhantomJsDriverManager.getInstance().setup();
			break;
			
		case "safari":
			break;

		default:
			throw new NotFoundException("Browser Not Found. Please Provide a Valid Browser");
		}
		new WebDriverFactory();
		WebDriver driver = WebDriverFactory.createDriver();
		setWebDriver(driver);
	}

	@BeforeMethod
	public void beforeMethod() throws Exception
	{
		System.out.println("Browser: "+Browser);
		System.out.println("WebsiteURL: "+WebsiteURL);
		new WebDriverFactory();
		WebDriver driver = WebDriverFactory.createDriver();
		setWebDriver(driver);
	}
	
	
	public static void setWebDriver(WebDriver driver)
	{
		wd.set(driver);
	}

	public static WebDriver getWebDriver() 
	{
		return wd.get();
	}

	@AfterMethod(alwaysRun=true,enabled=true)
	public void afterMethod() throws Exception
	{
		Thread.sleep(2000);
		getWebDriver().quit();	
	}
}
