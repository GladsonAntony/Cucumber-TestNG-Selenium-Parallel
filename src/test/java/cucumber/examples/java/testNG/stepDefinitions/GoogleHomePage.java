/**
 * @Author Gladson Antony
 * @Date 14-Mar-2018 
 * @Time 10:19:58 PM
 */
package cucumber.examples.java.testNG.stepDefinitions;

import controllers.BaseMethod;
import controllers.WebDriverFactory;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.openqa.selenium.support.PageFactory;
import pageObjects.initializePageObjects.PageFactoryInitializer;
import pageObjects.pages.GoogleHomePageObjects;

import static controllers.WebDriverFactory.getWebDriver;

public class GoogleHomePage
{
     public GoogleHomePageObjects googleHomePage()
    {
        return PageFactory.initElements(getWebDriver(), GoogleHomePageObjects.class);
    }

	@Given("^I'm on (.+)$")
    public void givenIAmOn(String URL) 
	{
        //getWebDriver().get(URL);
    }

    @When("^I would search for element header$")
    public void i_would_search_for_element_header() throws Exception
    {
        googleHomePage().verifyPageTitle();
    }

    @Then("^I might see this element$")
    public void thenIShouldSeeThisElement() throws Exception 
    {
    	googleHomePage().verifyPageTitle();
    }

}
