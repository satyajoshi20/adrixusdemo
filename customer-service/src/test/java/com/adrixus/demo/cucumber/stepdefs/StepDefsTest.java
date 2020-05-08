package com.adrixus.demo.cucumber.stepdefs;


import com.adrixus.demo.cucumber.CucumberContextConfiguration;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public class StepDefsTest extends CucumberContextConfiguration {

    private RestTemplate restTemplate = new RestTemplate();

    private ResponseEntity<String> response;


    @When("^the client calls /version$")
    public void the_client_issues_GET_version() throws Throwable{
        response = restTemplate.getForEntity("http://localhost:8082/customers/version", String.class);
        System.out.println("Response is - " + response);

    }

    @Then("^the client receives status code of (\\d+)$")
    public void the_client_receives_status_code_of(int statusCode) throws Throwable {
        System.out.println("statuscode is " + statusCode);
        assertThat("Error is there holy cow", response.getStatusCodeValue(), is(statusCode));

    }

    @And("^the client receives server version (.+)$")
    public void the_client_receives_server_version_body(String version) throws Throwable {
        System.out.println("version is: " + version);
        assertThat(response.getBody(), is(version));
    }
}
