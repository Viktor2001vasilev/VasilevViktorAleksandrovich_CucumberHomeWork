package stepDefs;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;

@CucumberOptions(
        features = {"src/test/java"}
)

public class RunnerTest extends AbstractTestNGCucumberTests {

}
