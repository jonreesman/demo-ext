package com.example.demoext.controller;

import com.example.demoext.domain.MessageEnvelope;
import com.example.demoext.domain.MessageName;
import com.example.demoext.domain.MessageType;
import com.example.demoext.domain.Person;
import com.example.demoext.repository.MessageRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.util.UUID;

@RestController
public class MessageController {

    private final KafkaTemplate<String, Object> template;

    @Autowired
    private MessageRepository messageRepository;

    @Autowired
    public MessageController(KafkaTemplate<String, Object> template) {
        this.template = template;
    }

    @PostMapping("/messages")
    String sendCommand(@RequestBody Person person, @RequestParam(defaultValue = "1") int repeatTimes) {

        String correlationId = UUID.randomUUID().toString();

        for (int i = 0; i < repeatTimes; i++) {
            Person personWithId = new Person(UUID.randomUUID().toString(), person.getFirstName(), person.getLastName(), person.getAge());

            MessageEnvelope messageEnvelope = new MessageEnvelope(MessageType.COMMAND, MessageName.PROCESS_PERSON, correlationId, personWithId);
            template.send("demo-command", messageEnvelope);
        }
        return correlationId;
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
