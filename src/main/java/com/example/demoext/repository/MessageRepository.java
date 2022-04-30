package com.example.demoext.repository;


import com.example.demoext.domain.MessageEnvelope;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface MessageRepository extends CrudRepository<MessageEnvelope, Integer> {
    @Query(value = "SELECT * FROM message_envelope WHERE correlation_id = ?1", nativeQuery = true)
    Iterable<MessageEnvelope> findAllByCorrelationId(String correlationId);

    @Query(value = "SELECT * FROM message_envelope WHERE id = ?1", nativeQuery = true)
    MessageEnvelope findById(String id);
}
