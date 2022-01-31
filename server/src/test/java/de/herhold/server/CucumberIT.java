package de.herhold.server;

import io.cucumber.junit.Cucumber;
import org.junit.platform.suite.api.ConfigurationParameter;
import org.junit.platform.suite.api.IncludeEngines;
import org.junit.platform.suite.api.SelectClasspathResource;
import org.junit.platform.suite.api.Suite;
import org.junit.runner.RunWith;

import static io.cucumber.junit.platform.engine.Constants.GLUE_PROPERTY_NAME;

@Suite
@IncludeEngines("cucumber")
@SelectClasspathResource("de/herhold/server")
@ConfigurationParameter(key = GLUE_PROPERTY_NAME, value = "de.herhold.server.cucumber")
@RunWith(Cucumber.class)
public class CucumberIT {
}
