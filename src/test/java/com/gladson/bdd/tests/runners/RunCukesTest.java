/**
 * @Author Gladson Antony
 * @Date 14-Mar-2018 
 * @Time 9:42:43 PM
 */
package com.gladson.bdd.tests.runners;

import cucumber.api.CucumberOptions;
import cucumber.api.testng.AbstractTestNGCucumberTests;

@CucumberOptions
		(
				features = { "src/test/resources/features/" },
				glue = {
						"com.gladson.bdd.tests.steps",
						"listeners.AllureListener",
						"com.gladson.bdd.main.controllers.WebDriverFactory" },
				plugin={
						"pretty",
						"json:target/Cucumber.json", "html:target/cucumber-html-report",
						"io.qameta.allure.cucumber2jvm.AllureCucumber2Jvm" }
		)
public class RunCukesTest extends AbstractTestNGCucumberTests {

}
