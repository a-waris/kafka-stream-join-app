package com.example.kafkastreamapp;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.common.errors.SerializationException;
import org.apache.kafka.common.serialization.Serializer;

import java.nio.charset.StandardCharsets;

import java.util.Map;

@Slf4j
public class CompositeKeySerializer implements Serializer<CompositeKey> {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public void configure(Map<String, ?> configs, boolean isKey) {
        // Configure serializer, if necessary
    }

    @Override
    public byte[] serialize(String topic, CompositeKey data) {
        if (data == null) {
            return null;
        }

        try {
            byte[] serializedData = objectMapper.writeValueAsBytes(data);
            log.info("Topic - {}, Serializing CompositeKey from data: {}", topic, new String(serializedData));  // logging the serialized data
            return serializedData;
        } catch (Exception e) {
            throw new SerializationException("Error serializing CompositeKey", e);
        }
    }

    @Override
    public void close() {
        // Close resources, if necessary
    }
}
