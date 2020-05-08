package com.adrixus.demo.service;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.requestreply.ReplyingKafkaTemplate;
import org.springframework.kafka.requestreply.RequestReplyFuture;
import org.springframework.stereotype.Service;

import java.util.concurrent.ExecutionException;

@Service
public class CustomerServiceImpl implements CustomerService {

    @Value("${customer.verify.topic}")
    private String customerVerifyTopic;

    private ReplyingKafkaTemplate<Object, String, String> replyingKafkaTemplate;

    public CustomerServiceImpl(ReplyingKafkaTemplate<Object, String, String> replyingKafkaTemplate) {
        this.replyingKafkaTemplate = replyingKafkaTemplate;
    }

    @Override
    public Boolean verifyCustomerId(Long customerId) throws ExecutionException, InterruptedException {
        ProducerRecord<Object, String> record = new ProducerRecord<>(customerVerifyTopic, null, null, customerId.toString());
        RequestReplyFuture<Object, String, String> future = replyingKafkaTemplate.sendAndReceive(record);
        ConsumerRecord<Object, String> response = future.get();
        System.out.println("Returned message is - " + response.value());
        return Boolean.valueOf(response.value());
    }
}
