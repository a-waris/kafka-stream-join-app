package com.example.kafkastreamapp;

import com.example.kafkastreamapp.Event1.Event1;
import com.example.kafkastreamapp.Event2.Event2;
import com.example.kafkastreamapp.Models.Key;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;


@Slf4j
@AllArgsConstructor
@Component
public class KafkaProducer {

    private final KafkaTemplate<CompositeKey, Event1> kafkaTemplateForEvent1;
    private final KafkaTemplate<CompositeKey, Event2> kafkaTemplateForEvent2;


    public void sendEvent1(String topic, Event1 event) {

        kafkaTemplateForEvent1.send(topic, convertToCompositeKey(event.getKey()), event)
                .whenComplete((result, ex) -> {
                    if (ex == null) {
                        log.info("Message sent to topic({}): {}", topic, event);
                    } else {
                        log.error("Failed to send message", ex);
                    }
                });
    }

    public void sendEvent2(String topic, Event2 event) {
        kafkaTemplateForEvent2.send(topic, convertToCompositeKey(event.getKey()), event)
                .whenComplete((result, ex) -> {
                    if (ex == null) {
                        log.info("Message sent to topic({}): {}", topic, event);
                    } else {
                        log.error("Failed to send message", ex);
                    }
                });
    }

    public CompositeKey convertToCompositeKey(Key key) {
        CompositeKey compositeKey = new CompositeKey();
        compositeKey.setEmpl_id(key.getEmpl_id());
        compositeKey.setDept(key.getDept());
        return compositeKey;
    }
}