package com.example.kafkastreamapp;

import com.example.kafkastreamapp.Event1.Event1;
import com.example.kafkastreamapp.Event2.Event2;
import com.example.kafkastreamapp.Models.JoinedEvent;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.admin.NewTopic;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.Serde;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.common.serialization.StringSerializer;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.kstream.Consumed;
import org.apache.kafka.streams.kstream.KStream;
import org.apache.kafka.streams.kstream.KTable;
import org.apache.kafka.streams.kstream.Produced;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.annotation.EnableKafkaStreams;
import org.springframework.kafka.annotation.KafkaStreamsDefaultConfiguration;
import org.springframework.kafka.config.KafkaStreamsConfiguration;
import org.springframework.kafka.config.TopicBuilder;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.serializer.JsonSerde;
import org.springframework.kafka.support.serializer.JsonSerializer;

import java.util.HashMap;
import java.util.Map;

import static org.apache.kafka.streams.StreamsConfig.*;

@Slf4j
@Configuration
@EnableKafkaStreams
public class KafkaConfig {

    @Value("${spring.kafka.bootstrap-servers}")
    private String bootstrapAddress;

    @Value("${spring.kafka.streams.stateDir:/tmp/kafka-streams}")
    private String stateStoreLocation;

    @Value("${topicX:X}")  // Default to "X" if property topicX is not set
    private String topicX;

    @Value("${topicY:Y}")  // Default to "Y" if property topicY is not set
    private String topicY;


    @Bean
    public KafkaTemplate<CompositeKey, Event1> kafkaTemplateForEvent1() {
        return new KafkaTemplate<>(new DefaultKafkaProducerFactory<>(producerConfigs()));
    }

    @Bean
    public KafkaTemplate<CompositeKey, Event2> kafkaTemplateForEvent2() {
        return new KafkaTemplate<>(new DefaultKafkaProducerFactory<>(producerConfigs()));
    }

    @Bean
    public JsonSerde<Event1> event1Serde() {
        return new JsonSerde<>(Event1.class);
    }

    @Bean
    public JsonSerde<Event2> event2Serde() {
        return new JsonSerde<>(Event2.class);
    }

    @Bean(name = KafkaStreamsDefaultConfiguration.DEFAULT_STREAMS_CONFIG_BEAN_NAME)
    KafkaStreamsConfiguration kStreamsConfig() {
        Map<String, Object> props = new HashMap<>();
        props.put(APPLICATION_ID_CONFIG, "streams-app");
        props.put(BOOTSTRAP_SERVERS_CONFIG, bootstrapAddress);
//        props.put(DEFAULT_KEY_SERDE_CLASS_CONFIG, Serdes.String().getClass().getName());
//        props.put(DEFAULT_VALUE_SERDE_CLASS_CONFIG, Serdes.String().getClass().getName());
        // configure the state location to allow tests to use clean state for every run
        props.put(STATE_DIR_CONFIG, stateStoreLocation);

        return new KafkaStreamsConfiguration(props);
    }

    @Bean
    NewTopic xTopic() {
        return TopicBuilder.name("X").partitions(1).replicas(1).build();
    }

    @Bean
    NewTopic yTopic() {
        return TopicBuilder.name("Y").partitions(1).replicas(1).build();
    }


    @Bean
    public KStream<CompositeKey, JoinedEvent> kStream(StreamsBuilder streamsBuilder, JsonSerde<Event1> event1Serde, JsonSerde<Event2> event2Serde) {

        log.info("Configuring Kafka Streams...");

        Serde<CompositeKey> compositeKeySerde = Serdes.serdeFrom(new CompositeKeySerializer(), new CompositeKeyDeserializer());

        KTable<CompositeKey, Event1> table1 = streamsBuilder
                .table(topicX, Consumed.with(compositeKeySerde, event1Serde));

        KTable<CompositeKey, Event2> table2 = streamsBuilder
                .table(topicY, Consumed.with(compositeKeySerde, event2Serde));

        KTable<CompositeKey, JoinedEvent> joinedTable = table1.join(
                table2,
                (event1, event2) -> {
                    JoinedEvent joinedEvent = new JoinedEvent();
                    joinedEvent.setEmpl_id(event1.getKey().getEmpl_id());
                    joinedEvent.setIs_fte(event1.getValue().getIs_fte());
                    joinedEvent.setDob(event1.getValue().getDob());
                    joinedEvent.setState(event2.getValue().getState());
                    joinedEvent.setCountry(event1.getValue().getCountry());  // assuming country is the same in both events
                    log.info("Joined Event: {}", joinedEvent);  // Log the result of the join operation

                    return joinedEvent;
                }
        );

        KStream<CompositeKey, JoinedEvent> joinedStream = joinedTable.toStream();
        joinedStream
                .peek((key, value) -> log.info("Sending Joined Event: {} with Key: {}", value, key))  // log the joined events before sending
                .to("joined-topic", Produced.with(compositeKeySerde, new JsonSerde<>(JoinedEvent.class)));

        log.info(streamsBuilder.build().describe().toString());  // Log the topology of the Kafka Streams application
        return joinedStream;
    }

    private Map<String, Object> producerConfigs() {
        Map<String, Object> props = new HashMap<>();
        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapAddress);
        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, CompositeKeySerializer.class);
        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class);
        return props;
    }

}