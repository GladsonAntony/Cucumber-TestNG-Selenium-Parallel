/**
 * 
 */
package pageObjects.pages;

import controllers.WebDriverFactory;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.testng.Assert;

import pageObjects.initializePageObjects.PageFactoryInitializer;
import ru.yandex.qatools.allure.annotations.Step;
import utils.ExplicitWaiting;

import static controllers.WebDriverFactory.getWebDriver;

/**
 * @author Gladson Antony
 * @date Sep 17, 2016
 * 
 */
public class GMailPageObjects extends PageFactoryInitializer
{
	@FindBy(xpath="//input[@type='email']")
	private WebElement emailIDTextBox;
	
	@FindBy(xpath="//span[contains(text(),'Next')]")
	private WebElement nextButton;

	@Step("To Enter Email ID and Click Next Button")
	public GMailPageObjects enterEmailID(String emailID) 
	{
		ExplicitWaiting.explicitWaitElementToBeClickable(emailIDTextBox,10);
		emailIDTextBox.sendKeys(emailID);	
		nextButton.click();
		return this;
	}

	@Step("Verify the Page Title of the GMail Page")
	public GMailPageObjects verifyPageTitle() throws Exception 
	{
		Assert.assertEquals(getWebDriver().getTitle(), "gagagasgasg");
		return this;
	}
	
	
	
}
