package com.adrixus.demo.web.rest;

import com.adrixus.demo.service.CustomerService;
import com.adrixus.demo.web.rest.vm.SaveCustomerRequest;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@RunWith(SpringRunner.class)
@WebMvcTest(CustomerResource.class)
public class CustomerResourceTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private CustomerService customerService;

    @Test
    public void saveCustomerTest() throws Exception {
        SaveCustomerRequest saveCustomerRequest = new SaveCustomerRequest();
        Long customerId = 75l;
        given(customerService.saveCustomer(saveCustomerRequest)).willReturn(customerId);

        mvc.perform(post("/customers")
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk());


    }


}
