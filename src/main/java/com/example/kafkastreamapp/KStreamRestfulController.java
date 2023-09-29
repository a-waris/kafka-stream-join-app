package com.example.kafkastreamapp;

import com.example.kafkastreamapp.Event1.Event1;
import com.example.kafkastreamapp.Event2.Event2;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/kstream")
@AllArgsConstructor
public class KStreamRestfulController {
    private final KafkaProducer kafkaProducer;

    @PostMapping("/sendEvent1")
    public void sendEvent1(@RequestParam String topic, @RequestBody Event1 event) {
        kafkaProducer.sendEvent1(topic, event);
    }

    @PostMapping("/sendEvent2")
    public void sendEvent2(@RequestParam String topic, @RequestBody Event2 event) {
        kafkaProducer.sendEvent2(topic, event);
    }

}
