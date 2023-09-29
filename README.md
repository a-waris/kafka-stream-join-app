# Kafka Stream Application Demonstrating a Join on 2 topics 

## Overview

This Kafka Stream Application demonstrates a stream processing setup using Spring Kafka. It defines Kafka producers, Kafka Stream processing topology, and other necessary configurations for stream processing.

## Components

### Models
- `Event1` and `Event2`: Represent the events to be processed.
- `JoinedEvent`: Represents the result of joining `Event1` and `Event2`.
- `CompositeKey`: Custom key used for joining.

### Configuration (`KafkaConfig`)
- Kafka Producer Configurations: Setup for producing `Event1` and `Event2`.
- Kafka Streams Configurations: Setup for processing streams and joining events.
- Topics: Topic configurations for `X` and `Y` topics.

### Kafka Streams Topology
The stream processing topology is defined in the `kStream` bean method within `KafkaConfig`. It reads from topics `X` and `Y`, joins the events on a composite key, and writes the resulting `JoinedEvent` to the `joined-topic`.

## Setup

1. **Kafka Cluster**: Ensure you have a running Kafka cluster.
2. **Application Properties**: Set the necessary properties in `application.properties` or `application.yml` for Kafka bootstrap servers and other configurations.
3. **Build and Run**: Build and run the application using your preferred IDE or from the command line with Maven or Gradle.

## Usage

1. **Produce Events**: Events of type `Event1` and `Event2` are produced to topics `X` and `Y` respectively using the configured Kafka templates.
2. **Stream Processing**: The application's Kafka Streams topology will continuously process the incoming events, join them, and produce the resulting `JoinedEvent` to the `joined-topic`.
3. **Inspect Results**: You can inspect the results by consuming from the `joined-topic` using a Kafka consumer.

## Code Structure

The main code structure is organized as follows:
- `src/main/java/com/example/kafkastreamapp` contains the main application configurations and stream processing setup.
- `src/main/java/com/example/kafkastreamapp/Models` contains the DTO classes for the events.
- `src/main/resources` contains the `application.properties` or `application.yml` file for configuring the Kafka bootstrap servers and other application configurations.

## Running the Application

```bash
# Using Gradle
 gradle bootRun
```

---
