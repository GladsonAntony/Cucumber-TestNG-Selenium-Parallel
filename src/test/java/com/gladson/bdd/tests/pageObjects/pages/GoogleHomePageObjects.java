/**
 * 
 */
package com.gladson.bdd.tests.pageObjects.pages;

import static com.gladson.bdd.main.controllers.WebDriverFactory.getWebDriver;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.testng.Assert;

import com.gladson.bdd.main.utils.AllureAttachments;
import com.gladson.bdd.main.utils.ExplicitWaiting;
import com.gladson.bdd.main.utils.RandomGenerator;

import io.qameta.allure.Step;

/**
 * @author ${Gladson Antony}
 * @date Sep 17, 2016
 * 
 */
public class GoogleHomePageObjects
{
	@FindBy(xpath="//a[text()='Gmail']")
	private WebElement GmailLink;

	@FindBy(id="lst-ib")
	private WebElement SearchBox;

	@Step("Click On Gmail Link in the Google Home Page")
	public GoogleHomePageObjects clickonGmailLink() throws Exception
	{
		ExplicitWaiting.explicitWaitElementToBeClickable(GmailLink,10);
		AllureAttachments.saveWebElement(getWebDriver(), GmailLink);
		/*Screenshot screenshot = new AShot().takeScreenshot(getWebDriver(), GmailLink);
		ImageIO.write(screenshot.getImage(), "PNG", new File(TestData + "div_element.png"));	*/
		GmailLink.click();
		//AllureAttachments.attachFileType_XLSX(ExcelFiles + "TestData.xlsx");
		return this;		
	}

	@Step("Enter text into the Search Box")
	public GoogleHomePageObjects enterTextToSearchBox() 
	{
		SearchBox.sendKeys(RandomGenerator.GenerateRandomEMAILIDs("google.com"));
		return this;	
	}

	@Step("Verify the Page Title of the Google Home Page")
	public GoogleHomePageObjects verifyPageTitle() throws Exception 
	{
		Assert.assertEquals(getWebDriver().getTitle(),"Googleqwewe");
		return this;
	}

}
