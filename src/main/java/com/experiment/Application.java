package com.experiment;

import com.experiment.consumer.KafkaConsumerFactory;
import com.experiment.producer.KafkaProducerFactory;

import java.util.Arrays;
import java.util.stream.IntStream;

public class Application {

    public static void main(String[] args) {
        // Consumer
//        KafkaConsumerFactory consumer = new KafkaConsumerFactory("with_ssl");
//        consumer.subscribe(Arrays.asList("test_topic"));
//        consumer.listen();

        // Producer
        KafkaProducerFactory kafkaProducerFactory = new KafkaProducerFactory();
        IntStream.range(0, 100000)
                .forEach(n -> {
                    kafkaProducerFactory.publishRecord("mytopic",
                            String.format("{\"id\":\"%d\",\"message\":\"Harlo%d\"}", n, n));
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                });
    }

}