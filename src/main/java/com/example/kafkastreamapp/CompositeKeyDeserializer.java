package com.example.kafkastreamapp;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.common.errors.SerializationException;
import org.apache.kafka.common.serialization.Deserializer;

import java.nio.charset.StandardCharsets;
import java.util.Map;

@Slf4j
public class CompositeKeyDeserializer implements Deserializer<CompositeKey> {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public void configure(Map<String, ?> configs, boolean isKey) {
        // Configure deserializer, if necessary
    }

    @Override
    public CompositeKey deserialize(String topic, byte[] data) {
        if (data == null) {
            return null;
        }

        try {
            log.info("Topic - {}, Deserializing CompositeKey from data: {}", topic, new String(data, StandardCharsets.UTF_8));  // logging the serialized data
            return objectMapper.readValue(data, CompositeKey.class);
        } catch (Exception e) {
            throw new SerializationException("Error deserializing CompositeKey from data: " + new String(data, StandardCharsets.UTF_8), e);

        }
    }

    @Override
    public void close() {
        // Close resources, if necessary
    }
}