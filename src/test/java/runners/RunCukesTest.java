/**
 * @Author Gladson Antony
 * @Date 14-Mar-2018 
 * @Time 9:42:43 PM
 */
package runners;

import cucumber.api.CucumberOptions;
import cucumber.api.testng.AbstractTestNGCucumberTests;

@CucumberOptions(features = "resources/features",
glue = {/*"listeners.AllureListener",*/"cucumber.examples.java.testNG.stepDefinitions"},
format={"pretty","html:target/html/"})

public class RunCukesTest extends AbstractTestNGCucumberTests
{

}
