/**
 * @Author Gladson Antony
 * @Date 14-Mar-2018 
 * @Time 9:42:43 PM
 */
package runners;

import cucumber.api.CucumberOptions;
import cucumber.api.testng.AbstractTestNGCucumberTests;

@CucumberOptions(
        features={"features"},
        glue={"steps"},
        format={"pretty","html:target/html/"},
        plugin = {"pretty", "json:target/Cucumber.json",
                "html:target/cucumber-html-report"})

public class RunCukesTest extends AbstractTestNGCucumberTests
{

}
