package com.adrixus.demo.cucumber.stepdefs;


import com.adrixus.demo.domain.Customer;
import com.adrixus.demo.web.rest.vm.ApiResponse;
import com.adrixus.demo.web.rest.vm.SaveCustomerRequest;
import com.adrixus.demo.web.rest.vm.SaveCustomerResponse;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class CreateCustomerStepDefs {

    @Autowired
    private TestRestTemplate restTemplate;

    private final SaveCustomerRequest saveCustomerRequest = createSaveCustomerRequest();
    private ResponseEntity<ApiResponse<SaveCustomerResponse>> saveCustomerResponse;
    private ResponseEntity<ApiResponse<Customer>> getCustomerResponse;

    @When("^the client calls /customers to save a new customer$")
    public void client_calls_api_to_add_new_customer() {
        HttpEntity<SaveCustomerRequest> httpEntity = new HttpEntity<>(saveCustomerRequest);
        saveCustomerResponse = restTemplate.exchange("/customers", HttpMethod.POST, httpEntity, new ParameterizedTypeReference<ApiResponse<SaveCustomerResponse>>() {});
    }

    @And("^the client calls /customers/id to fetch the customer$")
    public void client_calls_api_to_fetch_customer() {
        Long customerId = saveCustomerResponse.getBody().getData().getCustomerId();
        getCustomerResponse = restTemplate.exchange("/customers/" + customerId, HttpMethod.GET, null,
            new ParameterizedTypeReference<ApiResponse<Customer>>() {});
    }

    @Then("^the client receives the same customer as that was saved$")
    public void client_receives_customer_that_was_just_created() {
        Customer customer = getCustomerResponse.getBody().getData();
        assertThat(saveCustomerResponse.getBody().getData().getCustomerId(), is(customer.getId()));
        assertThat(saveCustomerRequest.getName(), is(customer.getName()));
        assertThat(saveCustomerRequest.getAddressLine1(), is(customer.getAddressLine1()));
        assertThat(saveCustomerRequest.getAddressLine2(), is(customer.getAddressLine2()));
        assertThat(saveCustomerRequest.getCity(), is(customer.getCity()));
        assertThat(saveCustomerRequest.getState(), is(customer.getState()));
        assertThat(saveCustomerRequest.getCountry(), is(customer.getCountry()));
    }

    private SaveCustomerRequest createSaveCustomerRequest()
    {
        SaveCustomerRequest saveCustomerRequest = new SaveCustomerRequest();
        saveCustomerRequest.setName("Peter Doyle");
        saveCustomerRequest.setAddressLine1("192 Corinthian street");
        saveCustomerRequest.setAddressLine2("polk");
        saveCustomerRequest.setCity("Des Moines");
        saveCustomerRequest.setState("Iowa");
        saveCustomerRequest.setCountry("USA");
        return saveCustomerRequest;
    }
}
