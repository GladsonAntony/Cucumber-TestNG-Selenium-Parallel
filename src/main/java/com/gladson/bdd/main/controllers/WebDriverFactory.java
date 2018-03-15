/**
 * 
 */
package com.gladson.bdd.main.controllers;

import org.openqa.selenium.NotFoundException;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;

import cucumber.api.Scenario;
import cucumber.api.java.After;
import cucumber.api.java.Before;
import io.github.bonigarcia.wdm.ChromeDriverManager;
import io.github.bonigarcia.wdm.EdgeDriverManager;
import io.github.bonigarcia.wdm.FirefoxDriverManager;
import io.github.bonigarcia.wdm.InternetExplorerDriverManager;
import io.github.bonigarcia.wdm.PhantomJsDriverManager;
import com.gladson.bdd.main.utils.AllureAttachments;

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
		case  "firefox_headless":
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

	public static void setWebDriver(WebDriver driver)
	{
		wd.set(driver);
	}

	public static WebDriver getWebDriver() 
	{
		return wd.get();
	}

	@After
	public void afterMethod() throws Exception
	{
		Thread.sleep(2000);
		getWebDriver().quit();
	}

	public static void embedScreenshot(Scenario scenario)
	{
		if ( scenario.isFailed() )
		{
			scenario.write("Current Page URL is: " + getWebDriver().getCurrentUrl());
			try
			{
				byte[] screenshot = ((TakesScreenshot) getWebDriver()).getScreenshotAs(OutputType.BYTES);
				scenario.embed(screenshot, "image/png");
				AllureAttachments.saveFullPageScreenshot(scenario.getName(),getWebDriver());
			}
			catch (WebDriverException somePlatformsDontSupportScreenshots)
			{	}
		}
	}
}
