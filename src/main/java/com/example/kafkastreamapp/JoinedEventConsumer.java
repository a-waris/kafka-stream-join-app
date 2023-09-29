package com.example.kafkastreamapp;

import com.example.kafkastreamapp.Models.JoinedEvent;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class JoinedEventConsumer {

    private static final String TOPIC = "joined-topic";

    private static final String GROUP_ID = "awaris";

    @KafkaListener(topics = TOPIC, groupId = GROUP_ID)
    public void consume(ConsumerRecord<CompositeKey, JoinedEvent> record) {
        CompositeKey key = record.key();
        JoinedEvent joinedEvent = record.value();
        // log the key and value
        log.info("Consumed record with key: {}, value: {}", key, joinedEvent);
    }
}
