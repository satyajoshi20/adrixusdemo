Feature: Testing a REST API
    Scenario: client makes call to GET /version
        When the client calls /version
        Then the client receives status code of 200
        And the client receives server version 1.0
