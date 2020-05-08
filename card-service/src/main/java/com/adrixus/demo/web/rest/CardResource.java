package com.adrixus.demo.web.rest;

import com.adrixus.demo.exception.AccountDoesNotExistException;
import com.adrixus.demo.exception.CardNotFoundException;
import com.adrixus.demo.exception.CustomerNotFoundException;
import com.adrixus.demo.service.CardService;
import com.adrixus.demo.web.rest.errors.FieldErrorVM;
import com.adrixus.demo.web.rest.vm.*;
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
import static org.springframework.http.HttpStatus.OK;

@RestController
@RequestMapping("/cards")
public class CardResource {

    private static final Logger LOGGER = LoggerFactory.getLogger(CardResource.class);

    private final CardService cardService;

    public CardResource(CardService cardService)
    {
        this.cardService = cardService;
    }

    /**
     * Save new card
     * @param saveCardRequest
     * @return
     */
    @PostMapping
    public ResponseEntity<ApiResponse<SaveCardResponse>> saveCard(@Valid @RequestBody SaveCardRequest saveCardRequest)
    {
        Long cardId = cardService.saveCard(saveCardRequest);
        LOGGER.info("Saved card successfully with id: {}", cardId);
        SaveCardResponse saveCardResponse = new SaveCardResponse(cardId);
        ApiResponse<SaveCardResponse> apiResponse = new ApiResponse<>(SUCCESS, "Card saved successfully", saveCardResponse);
        return new ResponseEntity<>(apiResponse, OK);
    }


    /**
     * Assign card to customer
     * @param cardId
     * @param assignCardToCustomerRequest
     * @return
     */
    @PostMapping("/{cardId}/assignCardToCustomer")
    public ResponseEntity assignCardToCustomer(@PathVariable Long cardId, @Valid @RequestBody AssignCardToCustomerRequest assignCardToCustomerRequest)
        throws ExecutionException, InterruptedException {
        try{
            cardService.assignCardToCustomer(cardId, assignCardToCustomerRequest.getCustomerId());
        }
        catch (CustomerNotFoundException ex)
        {
            FieldErrorVM fieldErrorVM = new FieldErrorVM(null, "customerId", "customerId " + ex.getCustomerId() + " does not exist");
            ApiResponse apiResponse = new ApiResponse(FAILURE, "Invalid request", null, singletonList(fieldErrorVM));
            return new ResponseEntity(apiResponse, HttpStatus.BAD_REQUEST);
        }

        ApiResponse apiResponse = new ApiResponse(SUCCESS,
            "Card id " + cardId + " assigned to customer id " + assignCardToCustomerRequest.getCustomerId() + " successfully", null);
        return new ResponseEntity(apiResponse, HttpStatus.OK);
    }


    /**
     * Assign card to account
     * @param cardId
     * @param assignCardToAccountRequest
     * @return
     */
    @PostMapping("/{cardId}/assignCardToAccount")
    public ResponseEntity assignCardToAccount(@PathVariable Long cardId, @Valid @RequestBody AssignCardToAccountRequest assignCardToAccountRequest)
        throws InterruptedException, ExecutionException, JsonProcessingException {
        try{
            cardService.assignCardToAccount(cardId, assignCardToAccountRequest.getAccountId());
        }
        catch (AccountDoesNotExistException ex)
        {
            FieldErrorVM fieldErrorVM = new FieldErrorVM(null, "accountId", "accountId " + ex.getAccountId() + " does not exist");
            ApiResponse apiResponse = new ApiResponse(FAILURE, "Invalid request", null, singletonList(fieldErrorVM));
            return new ResponseEntity(apiResponse, HttpStatus.BAD_REQUEST);
        }

        ApiResponse apiResponse = new ApiResponse(SUCCESS,
            "Card id " + cardId + " assigned to account id " + assignCardToAccountRequest.getAccountId() + " successfully", null);
        return new ResponseEntity(apiResponse, HttpStatus.OK);
    }

    @ExceptionHandler(CardNotFoundException.class)
    public ResponseEntity<ApiResponse> handleCardNotFoundException(CardNotFoundException ex)
    {
        FieldErrorVM fieldErrorVM = new FieldErrorVM(null, "cardId", "cardId " + ex.getCardId() + " does not exist");
        ApiResponse apiResponse = new ApiResponse(FAILURE, "Invalid request", null, singletonList(fieldErrorVM));
        return new ResponseEntity(apiResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponse> handleAllUnhandledException(Exception ex)
    {
        return new ResponseEntity(new ApiResponse(FAILURE, "Server Error", null), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
