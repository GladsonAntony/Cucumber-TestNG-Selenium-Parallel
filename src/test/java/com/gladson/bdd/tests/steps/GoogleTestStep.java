package com.gladson.bdd.tests.steps;

import com.gladson.bdd.main.controllers.WebDriverFactory;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.openqa.selenium.support.PageFactory;

import com.gladson.bdd.tests.pageObjects.pages.GoogleHomePageObjects;

import static com.gladson.bdd.main.controllers.WebDriverFactory.getWebDriver;

public class GoogleTestStep
{
    public GoogleHomePageObjects googleHomePage()
    {
        return PageFactory.initElements(getWebDriver(), GoogleHomePageObjects.class);
    }

    @Given("^I'm on Google$")
    public void i_m_on_Google()
    {
        System.out.println(getWebDriver().getTitle());
        getWebDriver().navigate().to("https://www.google.co.in/");
    }

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
