package com.adrixus.demo.web.rest;

import com.adrixus.demo.domain.Customer;
import com.adrixus.demo.service.CustomerService;
import com.adrixus.demo.web.rest.vm.SaveCustomerRequest;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.WebApplicationContext;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;


@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment= SpringBootTest.WebEnvironment.MOCK, classes = {CustomerResource.class, TestConfiguration.class})
public class CustomerResourceTest {

    private MockMvc mvc;

    private ObjectMapper objectMapper = new ObjectMapper();

    @MockBean
    private CustomerService customerService;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Before
    public void setUp() {
        this.mvc = webAppContextSetup(webApplicationContext).build();
    }

    @Test
    public void saveCustomerTest_Success() throws Exception {
        SaveCustomerRequest saveCustomerRequest = createSaveCustomerRequest();
        Long customerId = 75l;
        String inputReqBody = objectToJsonString(saveCustomerRequest);

        given(customerService.saveCustomer(any(SaveCustomerRequest.class))).willReturn(customerId);

        mvc.perform(post("/customers")
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .accept(MediaType.APPLICATION_JSON_VALUE)
            .content(inputReqBody))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.data.customerId").value("75"))
            .andExpect(jsonPath("$.status").value("SUCCESS"));

        verify(customerService, times(1)).saveCustomer(any(SaveCustomerRequest.class));
    }

    @Test
    public void saveCustomerTest_GeneralFailure() throws Exception {
        SaveCustomerRequest saveCustomerRequest = createSaveCustomerRequest();
        String inputReqBody = objectToJsonString(saveCustomerRequest);

        given(customerService.saveCustomer(any(SaveCustomerRequest.class))).willThrow(new RuntimeException("Error"));

        mvc.perform(post("/customers")
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .accept(MediaType.APPLICATION_JSON_VALUE)
            .content(inputReqBody))
            .andExpect(status().isInternalServerError())
            .andExpect(jsonPath("$.status").value("FAILURE"));

        verify(customerService, times(1)).saveCustomer(any(SaveCustomerRequest.class));
    }

    @Test
    public void getCustomerTest_Success() throws Exception {
        Long customerId = 11l;
        Customer customer = createCustomer();
        customer.setId(customerId);

        given(customerService.getCustomerById(customerId)).willReturn(Optional.of(customer));

        mvc.perform(get("/customers/" + customerId)
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .accept(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.status").value("SUCCESS"))
            .andExpect(jsonPath("$.data.id").value(customerId))
            .andExpect(jsonPath("$.data.name").value(customer.getName()));

        verify(customerService, times(1)).getCustomerById(customerId);
    }

    @Test
    public void getCustomerTest_Failure_CustomerNotFound() throws Exception {
        Long customerId = 20l;

        given(customerService.getCustomerById(customerId)).willReturn(Optional.ofNullable(null));

        mvc.perform(get("/customers/" + customerId)
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .accept(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(status().isNotFound())
            .andExpect(jsonPath("$.status").value("FAILURE"));

        verify(customerService, times(1)).getCustomerById(customerId);
    }

    @Test
    public void getCustomerTest_Failure_GeneralFailure() throws Exception {
        Long customerId = 28l;

        given(customerService.getCustomerById(customerId)).willThrow(new RuntimeException("Error"));

        mvc.perform(get("/customers/" + customerId)
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .accept(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(status().isInternalServerError())
            .andExpect(jsonPath("$.status").value("FAILURE"));

        verify(customerService, times(1)).getCustomerById(customerId);
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

    private Customer createCustomer()
    {
        Customer customer = new Customer();
        customer.setName("John Smith");
        customer.setAddressLine1("Shantam park");
        customer.setAddressLine2("race course");
        customer.setCity("vadodara");
        customer.setState("gujarat");
        customer.setCountry("India");
        return customer;
    }

    private String objectToJsonString(Object object) throws JsonProcessingException {
        return objectMapper.writeValueAsString(object);
    }

}
