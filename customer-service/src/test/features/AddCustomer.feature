Feature: Testing customer is created successfully

    Scenario: client makes calls to save and then fetch a new customer
        When the client calls /customers to save a new customer
        And the client calls /customers/id to fetch the customer
        Then the client receives the same customer as that was saved

