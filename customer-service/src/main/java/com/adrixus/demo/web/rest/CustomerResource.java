package com.adrixus.demo.web.rest;

import com.adrixus.demo.domain.Customer;
import com.adrixus.demo.service.CustomerService;
import com.adrixus.demo.web.rest.vm.ApiResponse;
import com.adrixus.demo.web.rest.vm.SaveCustomerRequest;
import com.adrixus.demo.web.rest.vm.SaveCustomerResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Optional;

import static com.adrixus.demo.web.rest.vm.ApiResponseStatus.FAILURE;
import static com.adrixus.demo.web.rest.vm.ApiResponseStatus.SUCCESS;
import static org.springframework.http.HttpStatus.OK;

@RestController
@RequestMapping(value = "/customers")
public class CustomerResource {

    private static final Logger LOGGER = LoggerFactory.getLogger(CustomerResource.class);

    private final CustomerService customerService;

    public CustomerResource(CustomerService customerService)
    {
        this.customerService = customerService;
    }


    /**
     * Save new customer
     * @param saveCustomerRequest
     * @return
     */
    @PostMapping
    public ResponseEntity<ApiResponse<SaveCustomerResponse>> saveCustomer(@Valid @RequestBody SaveCustomerRequest saveCustomerRequest)
    {
        Long customerId = customerService.saveCustomer(saveCustomerRequest);
        LOGGER.info("Saved customer successfully with customerId: {}", customerId);
        SaveCustomerResponse saveCustomerResponse = new SaveCustomerResponse(customerId);
        ApiResponse<SaveCustomerResponse> apiResponse = new ApiResponse(SUCCESS, "Customer saved successfully", saveCustomerResponse);
        return new ResponseEntity<>(apiResponse, OK);
    }

    @GetMapping("/version")
    public ResponseEntity<String> foo()
    {
        System.out.println("come here !!!!!");
        return new ResponseEntity<String>("1.0", HttpStatus.OK);
    }

    /**
     * Fetch customer by id
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<Customer>> getCustomer(@PathVariable Long id)
    {
        LOGGER.info("Fetching customer with id - {}", id);
        Optional<Customer> customerOptional = customerService.getCustomerById(id);

        if(customerOptional.isPresent())
        {
            return new ResponseEntity<>(new ApiResponse<>(SUCCESS, "Customer information retrieved successfully", customerOptional.get()), HttpStatus.OK);
        }

        return new ResponseEntity<>((new ApiResponse<>(FAILURE, "Customer with id " + id + " not found", null)), HttpStatus.NOT_FOUND);
    }

}
