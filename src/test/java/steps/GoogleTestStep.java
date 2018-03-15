package steps;

import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.openqa.selenium.support.PageFactory;
import pageObjects.pages.GoogleHomePageObjects;

import static controllers.WebDriverFactory.getWebDriver;

public class GoogleTestStep
{
    public GoogleHomePageObjects googleHomePage()
    {
        return PageFactory.initElements(getWebDriver(), GoogleHomePageObjects.class);
    }

    @Given("^I'm on Google$")
    public void i_m_on_Google()
    {    }

    @When("^I would search for element header$")
    public void i_would_search_for_element_header() throws Exception
    {
        googleHomePage().verifyPageTitle();
    }

    @Then("^I might see this element$")
    public void i_might_see_this_element() throws Exception
    {
        googleHomePage().verifyPageTitle();
    }
}
