/**
 * 
 */
package com.gladson.bdd.main.controllers;

import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.datatransfer.StringSelection;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.InputStream;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NotFoundException;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.opera.OperaDriver;
import org.openqa.selenium.opera.OperaOptions;
import org.openqa.selenium.phantomjs.PhantomJSDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.safari.SafariDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;

import com.gladson.bdd.main.utils.AllureAttachments;
import com.gladson.bdd.main.utils.DateAndTime;
import com.gladson.bdd.main.utils.EnvironmentSetup;

import cucumber.api.java.After;
import cucumber.api.java.Before;
import io.github.bonigarcia.wdm.ChromeDriverManager;
import io.github.bonigarcia.wdm.EdgeDriverManager;
import io.github.bonigarcia.wdm.FirefoxDriverManager;
import io.github.bonigarcia.wdm.InternetExplorerDriverManager;
import io.github.bonigarcia.wdm.OperaDriverManager;
import io.github.bonigarcia.wdm.PhantomJsDriverManager;

/**
 * @Author Gladson Antony
 * @Date 08-Feb-2017
 */
public class WebDriverFactory extends InitMethod
{
	public static WebDriver driver;
	public static DesiredCapabilities capabilities;
	public static ThreadLocal<WebDriver> wd = new ThreadLocal<WebDriver>();

	@BeforeTest(enabled = true, alwaysRun = true)
	@Before
	public void suiteSetup() throws Exception
	{
		System.out.println("Browser: "+Browser);
		System.out.println("WebsiteURL: "+WebsiteURL);

		switch(Browser.toLowerCase())
		{
			case "chrome":
			ChromeDriverManager.getInstance().setup();
			driver = new ChromeDriver();
			break;

		case "chrome_headless":
			ChromeDriverManager.getInstance().setup();
			ChromeOptions chromeOptions = new ChromeOptions();
			chromeOptions.addArguments("--headless");
			chromeOptions.addArguments("--disable-gpu");
			driver = new ChromeDriver(chromeOptions);
			break;

		case "opera":
			OperaDriverManager.getInstance().version("2.33").setup();
			OperaOptions operaOptions = new OperaOptions();
			operaOptions.setBinary(BrowserBinary);
	        driver = new OperaDriver(operaOptions);
			break;

		case  "firefox":
			FirefoxDriverManager.getInstance().setup();
			driver = new FirefoxDriver();
			break;

		case  "firefox_headless":
			FirefoxDriverManager.getInstance().setup();
			FirefoxOptions options = new FirefoxOptions();
			options.setHeadless(true);
			driver = new FirefoxDriver(options);
			break;

		case  "ie":
		case "internet explorer":
			InternetExplorerDriverManager.getInstance().setup();
			DesiredCapabilities ieCapabilities = DesiredCapabilities.internetExplorer();
			ieCapabilities.setCapability(InternetExplorerDriver.INTRODUCE_FLAKINESS_BY_IGNORING_SECURITY_DOMAINS,true);
			break;	

		case  "edge":
			EdgeDriverManager.getInstance().setup();
			driver = new EdgeDriver();
			break;

		case "ghost":
		case "phantom":
			PhantomJsDriverManager.getInstance().setup();
			driver = new PhantomJSDriver();
			WebDriverWait wait = new WebDriverWait(driver, 30);
			wait.until(ExpectedConditions.urlContains(WebsiteURL));
			break;
			
		case "safari":
			driver = new SafariDriver();
			break;

		default:
			throw new NotFoundException("Browser Not Found. Please Provide a Valid Browser");
		}

		if(ImplicitlyWait > 0)
		{
			implicitlywait(ImplicitlyWait);
		}

		if(MaxPageLoadTime > 0)
		{
			setMaxPageLoadTime(MaxPageLoadTime);
		}
		driver.get(WebsiteURL);
		if(!Browser.toLowerCase().contains("unit") || !Browser.toLowerCase().contains("ghost") || !Browser.toLowerCase().contains("phantom"))
		{
			driver.manage().window().maximize();
		}
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

	@AfterTest(enabled = true,alwaysRun = true)
    @After
	public void afterMethod() throws Exception
	{
        EnvironmentSetup.environmentSetup();
        takeScreenShot();
        Thread.sleep(2000);
        attachSnapshotToReport();
		Thread.sleep(2000);
		getWebDriver().quit();
	}

	public static void implicitlywait(int timeInSeconds) throws Exception
	{
		driver.manage().timeouts().implicitlyWait(timeInSeconds, TimeUnit.SECONDS);
	}

	public static void setMaxPageLoadTime(int timeInSeconds) throws Exception
	{
		driver.manage().timeouts().pageLoadTimeout(timeInSeconds, TimeUnit.SECONDS);
	}

  /*  @AfterTest
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
	}*/

    public String takeScreenShot() throws Exception
    {
        File scrFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
        String snapshotFileName = "screenshot" + DateAndTime.getDate() + DateAndTime.getTime() + ".png";
        String pathToSnapshot = "./src/test/resources/snapshots" + "/" + snapshotFileName;
        FileUtils.copyFile(scrFile, new File(pathToSnapshot));
        return snapshotFileName;
    }

    public void attachSnapshotToReport()
    {
        Path content = null;
        String snapshotFileName = null;
        String snapshotFileLocation = null;
        try
        {
            snapshotFileName = takeScreenShot();
        }
        catch (Exception e2)
        {
            e2.printStackTrace();
        }
        content = Paths.get("./src/test/resources/snapshots"  + "/" + snapshotFileName);
        snapshotFileLocation = content.toString();
        try (InputStream is = Files.newInputStream(content))
        {
            AllureAttachments.attachFileType_PNG(snapshotFileLocation);
        } catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    public String getUrlTitle() throws Exception
    {
        URL aURL = new URL(WebsiteURL);
        String WebName = aURL.getHost();
        String WebSiteName = WebName.toUpperCase();
        return WebSiteName;
    }


	//* To Press ENTER Key using Robot *//*
    public void hitEnter() throws Exception
    {
        re = new Robot();
        re.keyPress(KeyEvent.VK_ENTER);
        re.keyRelease(KeyEvent.VK_ENTER);
    }


	//* To Press BACKSPACE Key using Robot *//*
    public void hitBackspace() throws Exception
    {
        re = new Robot();
        re.keyPress(KeyEvent.VK_BACK_SPACE);
        re.keyRelease(KeyEvent.VK_BACK_SPACE);
    }


	//* To Press DELETE Key using Robot *//*
    public void hitDelete() throws Exception
    {
        re = new Robot();
        re.keyPress(KeyEvent.VK_DELETE);
        re.keyRelease(KeyEvent.VK_DELETE);
    }


	//* To Select all the Text/Web Elements using ROBOT *//*
    public void selectAll() throws Exception
    {
        re = new Robot();
        re.keyPress(KeyEvent.VK_CONTROL);
        re.keyPress(KeyEvent.VK_A);
        re.keyRelease(KeyEvent.VK_CONTROL);
        re.keyRelease(KeyEvent.VK_A);
    }


	//* To Copy all the Selected Text/Web Elements using ROBOT *//*
    public void copyAll() throws Exception
    {
        re = new Robot();
        re.keyPress(KeyEvent.VK_CONTROL);
        re.keyPress(KeyEvent.VK_C);
        re.keyRelease(KeyEvent.VK_CONTROL);
        re.keyRelease(KeyEvent.VK_C);
    }


	//* To Paste all the Selected Text/Web Elements using ROBOT *//*
    public void pasteAll() throws Exception
    {
        re = new Robot();
        re.keyPress(KeyEvent.VK_CONTROL);
        re.keyPress(KeyEvent.VK_V);
        re.keyRelease(KeyEvent.VK_CONTROL);
        re.keyRelease(KeyEvent.VK_V);
    }



	//* To ZoomOut *//*
    public void robotZoomOut() throws Exception
    {
        re = new Robot();
        re.keyPress(KeyEvent.VK_CONTROL);
        re.keyPress(KeyEvent.VK_SUBTRACT);
        re.keyRelease(KeyEvent.VK_SUBTRACT);
        re.keyRelease(KeyEvent.VK_CONTROL);
    }


	//* To ZoomIn *//*
    public void robotZoomIn() throws Exception
    {
        re = new Robot();
        re.keyPress(KeyEvent.VK_CONTROL);
        re.keyPress(KeyEvent.VK_ADD);
        re.keyRelease(KeyEvent.VK_ADD);
        re.keyRelease(KeyEvent.VK_CONTROL);
    }


	//* To ScrollUp using ROBOT *//*
    public void robotScrollUp() throws Exception
    {
        re = new Robot();
        re.keyPress(KeyEvent.VK_PAGE_UP);
        re.keyRelease(KeyEvent.VK_PAGE_UP);
    }


	//* To ScrollDown using ROBOT *//*
    public void robotScrollDown() throws Exception
    {
        re = new Robot();
        re.keyPress(KeyEvent.VK_PAGE_DOWN);
        re.keyRelease(KeyEvent.VK_PAGE_DOWN);
    }


	//* To ScrollUp using JavaScript Executor *//*
    public void scrollUp() throws Exception
    {
        ((JavascriptExecutor) getWebDriver()).executeScript("scroll(0, -100);");
    }


	//* To ScrollDown using JavaScript Executor *//*
    public void scrollDown() throws Exception
    {
        ((JavascriptExecutor) getWebDriver()).executeScript("scroll(0, 100);");
    }


	//* To Move cursor to the Desired Location *//*
    public void moveCursor(int X_Position, int Y_Position) throws Exception
    {
        re.mouseMove(X_Position, Y_Position);
    }


	//* To Accept the Alert Dialog Message *//*
    public void alertAccept() throws Exception
    {
        al = getWebDriver().switchTo().alert();
        al.accept();
    }


	//* To Dismiss the Alert Dialog Message *//*
    public void alertDismiss() throws Exception
    {
        al = getWebDriver().switchTo().alert();
        al.dismiss();
    }


	//* To Get the Alert Messages *//*
    public String getAlertText() throws Exception
    {
        al = getWebDriver().switchTo().alert();
        String text = al.getText();
        Thread.sleep(2000);
        alertAccept();
        return text;
    }


	//* To Upload a File using JAVA AWT ROBOT *//*
    public void fileUpload(String FileToUpload) throws Exception
    {
        Thread.sleep(5000);
        StringSelection filetocopy = new StringSelection(FileToUpload);
        Toolkit.getDefaultToolkit().getSystemClipboard().setContents(filetocopy, null);
        Thread.sleep(1000);
        re = new Robot();
        re.keyPress(KeyEvent.VK_CONTROL);
        re.keyPress(KeyEvent.VK_V);
        re.keyRelease(KeyEvent.VK_V);
        re.keyRelease(KeyEvent.VK_CONTROL);
        re.keyPress(KeyEvent.VK_ENTER);
        re.keyRelease(KeyEvent.VK_ENTER);
    }


	//* To Perform a WebAction of Mouse Over *//*
    public void mousehover(WebElement element)
    {
        ac = new Actions(getWebDriver());
        ac.moveToElement(element).build().perform();
    }


	//* To Perform Select Option by Visible Text *//*
    public void selectByVisibleText(WebElement element, String value)
    {
        se = new Select(element);
        se.selectByVisibleText(value);
    }


	//* To Perform Select Option by Index *//*
    public void selectByIndex(WebElement element, int value)
    {
        se = new Select(element);
        se.selectByIndex(value);
    }


	//* To Perform Select Option by Value *//*
    public void selectByValue(WebElement element, String value)
    {
        se = new Select(element);
        se.selectByValue(value);
    }


	//* To click a certain Web Element *//*
    public void click(WebElement element)
    {
        element.click();
    }

    //* To click a certain Web Element using DOM/ JavaScript Executor *//*
    public void JSclick(WebElement element)
    {
        ((JavascriptExecutor) getWebDriver()).executeScript("return arguments[0].click();", element);
    }


	//* To Type at the specified location *//*
    public void sendKeys(WebElement element, String value)
    {
        element.sendKeys(value);
    }


	//* To Clear the content in the input location *//*
    public void clear(WebElement element)
    {
        element.clear();
    }


	//* To Drag and Drop from Source Locator to Destination Locator *//*
    public void dragandDrop(WebElement Source, WebElement Destination)
    {
        ac = new Actions(getWebDriver());
        ac.dragAndDrop(Source, Destination);
    }


	//*To Drag from the given WebElement Location and Drop at the given WebElement location *//*
    public void dragandDropTo(WebElement Source, int XOffset, int YOffset) throws Exception
    {
        ac = new Actions(getWebDriver());
        ac.dragAndDropBy(Source, XOffset, YOffset);
    }


	//*To Open a Page in New Tab *//*
    public void rightClick(WebElement element)
    {
        ac = new Actions(getWebDriver());
        ac.contextClick(element);
        ac.build().perform();
    }


	//*To Close Current Tab *//*
    public void closeCurrentTab()
    {
        getWebDriver().close();
    }


	//*To Perform Click and Hold Action *//*
    public void clickAndHold(WebElement element)
    {
        ac = new Actions(getWebDriver());
        ac.clickAndHold(element);
        ac.build().perform();
    }


	//*To Perform Click and Hold Action *//*
    public void doubleClick(WebElement element)
    {
        ac = new Actions(getWebDriver());
        ac.doubleClick(element);
        ac.build().perform();
    }


	//*To Switch To Frame By Index *//*
    public void switchToFrameByIndex(int index) throws Exception
    {
        getWebDriver().switchTo().frame(index);
    }


	//*To Switch To Frame By Frame Name *//*
    public void switchToFrameByFrameName(String frameName) throws Exception
    {
        getWebDriver().switchTo().frame(frameName);
    }


	//*To Switch To Frame By Web Element *//*
    public void switchToFrameByWebElement(WebElement element) throws Exception
    {
        getWebDriver().switchTo().frame(element);
    }


	//*To Switch out of a Frame *//*
    public void switchOutOfFrame() throws Exception
    {
        getWebDriver().switchTo().defaultContent();
    }


	//*To Get Tooltip Text *//*
    public String getTooltipText(WebElement element)
    {
        String tooltipText = element.getAttribute("title").trim();
        return tooltipText;
    }


	//*To Close all Tabs/Windows except the First Tab *//*
    public void closeAllTabsExceptFirst()
    {
        ArrayList<String> tabs = new ArrayList<String> (getWebDriver().getWindowHandles());
        for(int i=1;i<tabs.size();i++)
        {
            getWebDriver().switchTo().window(tabs.get(i));
            getWebDriver().close();
        }
        getWebDriver().switchTo().window(tabs.get(0));
    }


	//*To Print all the Windows *//*
    public void printAllTheWindows()
    {
        ArrayList<String> al = new ArrayList<String>(getWebDriver().getWindowHandles());
        for(String window : al)
        {
            System.out.println(window);
        }
    }
}
