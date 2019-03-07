package com.experiment;

import com.experiment.consumer.KafkaConsumerFactory;
import com.experiment.consumer.KafkaConsumerWrapper;
import com.experiment.producer.KafkaProducerFactory;
import com.experiment.producer.KafkaProducerWrapper;

import java.util.Arrays;
import java.util.stream.IntStream;

public class Application {

    public static void main(String[] args) {
        // Consumer
        KafkaConsumerWrapper consumer = KafkaConsumerFactory.withSsl("localhost:9098", "secured_group");
        consumer.subscribe(Arrays.asList("my_secured_topic"));
        consumer.listen();

        // Producer
        KafkaProducerWrapper kafkaProducer = KafkaProducerFactory.withSSL("localhost:9098");
        IntStream.range(0, 100000)
                .forEach(n -> {
                    kafkaProducer.publishRecord("my_secured_topic",
                            String.format("{\"id\":\"%d\",\"message\":\"Harlo%d\"}", n, n));
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                });

    }

}