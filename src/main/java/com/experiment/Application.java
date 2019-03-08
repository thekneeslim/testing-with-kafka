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
//        KafkaConsumerWrapper consumer = KafkaConsumerFactory.withSsl("localhost:9098", "secured_group");
//        consumer.subscribe(Arrays.asList("my_secured_topic"));
//        consumer.listen();

        // Producer
        KafkaProducerWrapper kafkaProducer = KafkaProducerFactory.withoutSsl("localhost:9093");
//        KafkaProducerWrapper kafkaProducer = KafkaProducerFactory.withAvro("localhost:9098", "http://0.0.0.0:8083");
        IntStream.range(0, 100000)
                .forEach(n -> {
                    kafkaProducer.publishRecord("my_unsecured_avro_topic",
                            String.format("{\"f1\":\"Message%d\"}", n, n));
//                            String.format("{\"id\":\"%d\",\"message\":\"Harlo%d\"}", n, n));
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                });






    }

}