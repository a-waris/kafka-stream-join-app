package com.example.kafkastreamapp.Event1;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.kafka.common.errors.SerializationException;
import org.apache.kafka.common.serialization.Deserializer;

public class Event1Deserializer implements Deserializer<Event1> {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public Event1 deserialize(String topic, byte[] data) {
        try {
            return objectMapper.readValue(data, Event1.class);
        } catch (Exception e) {
            throw new SerializationException("Error deserializing Event1", e);
        }
    }
}