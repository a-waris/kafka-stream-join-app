package com.example.kafkastreamapp.Event2;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.kafka.common.errors.SerializationException;
import org.apache.kafka.common.serialization.Deserializer;

public class Event2Deserializer implements Deserializer<Event2> {
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public Event2 deserialize(String topic, byte[] data) {
        try {
            return objectMapper.readValue(data, Event2.class);
        } catch (Exception e) {
            throw new SerializationException("Error deserializing Event1", e);
        }
    }
}