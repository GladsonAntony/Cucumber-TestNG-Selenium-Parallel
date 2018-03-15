/**
 * @Author Gladson Antony
 * @Date 14-Mar-2018 
 * @Time 9:42:43 PM
 */
package runners;

import cucumber.api.CucumberOptions;
import cucumber.api.testng.AbstractTestNGCucumberTests;

@CucumberOptions(features = "resources/features",
glue = {"listeners.AllureListener",
        "src/test/java/stepDefinitions"},
format={"pretty","html:target/html/"},
plugin = {"com.github.kirlionik.cucumberallure.AllureReporter",
        "pretty", "json:target/Cucumber.json",
        "html:target/cucumber-html-report"})

public class RunCukesTest extends AbstractTestNGCucumberTests
{

}
