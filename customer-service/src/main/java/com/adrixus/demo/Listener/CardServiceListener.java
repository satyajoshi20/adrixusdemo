package com.adrixus.demo.Listener;

import com.adrixus.demo.repository.CustomerRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Component;

@Component
public class CardServiceListener {

    private static final Logger LOGGER = LoggerFactory.getLogger(CardServiceListener.class);

    private static final String CUSTOMER_VERIFY_TOPIC = "customerVerify";

    private final CustomerRepository customerRepository;

    public CardServiceListener(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    @KafkaListener(topics = CUSTOMER_VERIFY_TOPIC, groupId = "2")
    @SendTo
    public String verifyCustomer(String customerId) {
        LOGGER.info("Received message: {}", customerId);
        Boolean isValidCustomer = customerRepository.findById(Long.valueOf(customerId)).isPresent();
        return isValidCustomer.toString();
    }
}
