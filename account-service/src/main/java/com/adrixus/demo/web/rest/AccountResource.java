package com.adrixus.demo.web.rest;

import com.adrixus.demo.Exception.AccountDoesNotExistException;
import com.adrixus.demo.service.AccountService;
import com.adrixus.demo.web.rest.errors.FieldErrorVM;
import com.adrixus.demo.web.rest.vm.AccountDto;
import com.adrixus.demo.web.rest.vm.ApiResponse;
import com.adrixus.demo.web.rest.vm.SaveAccountRequest;
import com.adrixus.demo.web.rest.vm.SaveAccountResponse;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import java.util.concurrent.ExecutionException;

import static com.adrixus.demo.web.rest.vm.ApiResponseStatus.FAILURE;
import static com.adrixus.demo.web.rest.vm.ApiResponseStatus.SUCCESS;
import static java.util.Collections.singletonList;

@RestController
@RequestMapping("/accounts")
public class AccountResource {

    private static final Logger LOGGER = LoggerFactory.getLogger(AccountResource.class);

    private final AccountService accountService;

    public AccountResource(AccountService accountService)
    {
        this.accountService = accountService;
    }

    /**
     * Save new account
     * @param saveAccountRequest
     * @return
     */
    @PostMapping
    public ResponseEntity<ApiResponse<SaveAccountResponse>> saveAccount(@Valid @RequestBody SaveAccountRequest saveAccountRequest)
    {
        Long accountId = accountService.saveAccount(saveAccountRequest);
        LOGGER.info("Saved account successfully with accountId: {}", accountId);
        SaveAccountResponse saveAccountResponse = new SaveAccountResponse(accountId);
        ApiResponse<SaveAccountResponse> apiResponse = new ApiResponse<>(SUCCESS, "Accout saved successfully", saveAccountResponse);
        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<AccountDto>> getAccount(@PathVariable Long id) throws InterruptedException, ExecutionException, JsonProcessingException {
        AccountDto response = null;
        try {
            response = accountService.getAccount(id);
        }
        catch (AccountDoesNotExistException ex)
        {
            FieldErrorVM fieldErrorVM = new FieldErrorVM(null, "accountId", "accountId " + ex.getAccountId() + " does not exist");
            ApiResponse apiResponse = new ApiResponse(FAILURE, "Invalid request", null, singletonList(fieldErrorVM));
            return new ResponseEntity(apiResponse, HttpStatus.BAD_REQUEST);
        }

        ApiResponse<AccountDto> apiResponse = new ApiResponse<>(SUCCESS, "Account details fetched successfully", response);
        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponse> handleAllUnhandledException(Exception ex)
    {
        return new ResponseEntity(new ApiResponse(FAILURE, "Server Error", null), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
