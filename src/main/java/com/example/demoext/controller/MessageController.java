package com.example.demoext.controller;

import com.example.demoext.domain.MessageEnvelope;
import com.example.demoext.repository.MessageRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

@RestController
public class MessageController {

    private final KafkaTemplate<String, Object> template;

    @Autowired
    private MessageRepository messageRepository;

    @Autowired
    public MessageController(KafkaTemplate<String, Object> template) {
        this.template = template;
    }

    @GetMapping("/persons/{id}")
    String getById(@PathVariable(value="id") String id) {
        try {
            return messageRepository.findById(id).toString();
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "no entity found with that id");
        }
    }

    @GetMapping("/persons/correlationId/{correlationId}")
    String getAllByCorrelationId(@PathVariable(value="correlationId") String correlationId) {
        try {
            Iterable<MessageEnvelope> messages = messageRepository.findAllByCorrelationId(correlationId);
            return messages.toString();
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "no entity with that correlationId found: ");
        }
    }
}
