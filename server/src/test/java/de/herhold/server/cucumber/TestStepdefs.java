package de.herhold.server.cucumber;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.cucumber.spring.CucumberContextConfiguration;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestStepdefs {

    private String test;
    private String test2;

    @Given("a test")
    public void aTest() {
        test = "aTest";
    }

    @When("i test")
    public void iTest() {
        test2 = "iTested";
    }

    @Then("its successful")
    public void itsSuccessful() {
        assertEquals("aTest", test);
        assertEquals("iTested", test2);
    }
}
