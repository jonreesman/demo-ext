package com.example.demoext.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;


@Entity
public class MessageEnvelope {

    public MessageEnvelope() {
    }

    public MessageEnvelope(MessageType type, MessageName name, String correlationId, Person payload) {
        this.type = type;
        this.name = name;
        this.correlationId = correlationId;
        this.payload = payload;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer messageId;

    MessageType type;
    MessageName name;
    String correlationId;
    Person payload;

    public MessageType getType() {
        return type;
    }

    public void setType(MessageType type) {
        this.type = type;
    }

    public MessageName getName() {
        return name;
    }

    public void setName(MessageName name) {
        this.name = name;
    }

    public String getCorrelationId() {
        return correlationId;
    }

    public void setCorrelationId(String correlationId) {
        this.correlationId = correlationId;
    }

    public Person getPayload() {
        return payload;
    }

    public void setPayload(Person payload) {
        this.payload = payload;
    }

    @Override
    public String toString() {
        return "MessageEnvelope{" +
                "type=" + type +
                ", name=" + name +
                ", correlationId='" + correlationId + '\'' +
                ", payload=" + payload +
                '}';
    }
}