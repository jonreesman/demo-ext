package com.example.demoext.consumer;

import com.example.demoext.domain.MessageEnvelope;
import com.example.demoext.domain.MessageName;
import com.example.demoext.domain.MessageType;
import com.example.demoext.repository.MessageRepository;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;


@Component
public class KafkaConsumer {

    @Autowired
    private MessageRepository messageRepository;

    private static final Logger logger = LoggerFactory.getLogger(KafkaConsumer.class);
    private final KafkaTemplate<String, Object> template;

    @Autowired
    public KafkaConsumer(KafkaTemplate<String, Object> template) {
        this.template = template;
    }

    @KafkaListener(topics = {"demo-public"}, groupId = "demo", containerFactory = "kafkaListenerContainerFactory")
    public void consumeProcessed(ConsumerRecord<String, String> consumerRecord, @Payload MessageEnvelope messageEnvelope) {
        if (messageEnvelope.getName() != MessageName.PERSON_PROCESSED) {
            return;
        }
        logger.info("Received: Payload: {} | Record: {}", messageEnvelope, consumerRecord.toString());

        messageRepository.save(messageEnvelope);
        MessageEnvelope outboundMessage = new MessageEnvelope(MessageType.EVENT, MessageName.PERSON_PROCESSED, messageEnvelope.getCorrelationId(), messageEnvelope.getPayload());

        switch (consumerRecord.topic()) {
            case "demo-public":
                template.send("secondary-public", outboundMessage);
                break;
            default:
                //DO Nothing
        }
                
    }
}