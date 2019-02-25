package com.experiment;

import com.experiment.consumer.KafkaConsumerFactory;
import com.experiment.producer.KafkaProducerFactory;

import java.util.Arrays;
import java.util.stream.IntStream;

public class Application {

    public static void main(String[] args) {
        // Consumer
        KafkaConsumerFactory consumer = new KafkaConsumerFactory("my-fifth-application");
        consumer.subscribe(Arrays.asList("first_topic"));
        consumer.listen();

        // Producer
        KafkaProducerFactory kafkaProducerFactory = new KafkaProducerFactory();
        IntStream.range(0, 10)
                .forEach(n -> kafkaProducerFactory.publishRecord("first_topic",
                        String.format("hello word %d", n),
                        String.format("id_%d", n)));
    }

}
