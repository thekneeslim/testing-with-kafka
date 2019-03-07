package com.experiment.producer;

import com.experiment.utils.KafkaClientPropertyBuilder;
import org.apache.kafka.clients.producer.KafkaProducer;

import java.util.Properties;

public class KafkaProducerFactory {

    private KafkaProducer<String, String> producer;

    public static KafkaProducerWrapper withoutSsl(String serverPort) {
        Properties properties = new KafkaClientPropertyBuilder()
                .withServer(serverPort)
                .withStringSerializer()
                .build();
        return new KafkaProducerWrapper(properties);
    }

    public static KafkaProducerWrapper withSSL(String serverPort) {
        Properties properties = new KafkaClientPropertyBuilder()
                .withServer(serverPort)
                .withStringSerializer()
                .withSsl()
                .build();
        return new KafkaProducerWrapper(properties);
    }

}
