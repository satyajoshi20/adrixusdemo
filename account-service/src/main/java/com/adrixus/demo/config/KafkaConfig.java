package com.adrixus.demo.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.listener.ConcurrentMessageListenerContainer;
import org.springframework.kafka.requestreply.ReplyingKafkaTemplate;

@Configuration
public class KafkaConfig {

    @Value("${card.info.response.topic}")
    private String cardInfoResponseTopic;

    @Bean
    public KafkaTemplate<Object, String> replyTemplate(ProducerFactory<Object, String> pf,
                                                       ConcurrentKafkaListenerContainerFactory<Object, String> factory) {
        KafkaTemplate<Object, String> kafkaTemplate = new KafkaTemplate<>(pf);
        factory.getContainerProperties().setMissingTopicsFatal(false);
        factory.setReplyTemplate(kafkaTemplate);
        return kafkaTemplate;
    }

    @Bean
    public ReplyingKafkaTemplate<Object, String, String> replyingKafkaTemplate(ProducerFactory<Object, String> pf,
                                                                               ConcurrentKafkaListenerContainerFactory<Object, String> factory) {
        ConcurrentMessageListenerContainer<Object, String> replyContainer = factory.createContainer(cardInfoResponseTopic);
        replyContainer.getContainerProperties().setMissingTopicsFatal(false);
        replyContainer.getContainerProperties().setGroupId("5");
        return new ReplyingKafkaTemplate<>(pf, replyContainer);
    }
}
