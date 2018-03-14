/**
 * @Author Gladson Antony
 * @Date 14-Mar-2018 
 * @Time 10:19:58 PM
 */
package cucumber.examples.java.testNG.stepDefinitions;

import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import pageObjects.initializePageObjects.PageFactoryInitializer;

public class GoogleHomePage extends PageFactoryInitializer
{
	
	@Given("^I'm on (.+)$")
    public void givenIAmOn(String URL) 
	{
        getWebDriver().get(URL);
    }

    @When("^I would search for element$")
    public void whenISearchForElement(String element_id) throws Exception 
    {
    	googleHomePage().verifyPageTitle();
    }

    @Then("^I might see this element$")
    public void thenIShouldSeeThisElement() throws Exception 
    {
    	googleHomePage().verifyPageTitle();
    }

}
