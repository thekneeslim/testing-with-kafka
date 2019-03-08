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
                .withProducerSsl()
                .build();
        return new KafkaProducerWrapper(properties);
    }

    public static KafkaProducerWrapper withAvro(String serverPort, String registry) {
        Properties properties = new KafkaClientPropertyBuilder()
                .withServer(serverPort)
                .withAvroSerializers(registry)
                .withSchema("'{\"type\":\"record\",\"name\":\"myrecord\",\"fields\":[{\"name\":\"f1\",\"type\":\"string\"}]}'")
                .build();
        return new KafkaProducerWrapper(properties);
    }

    public static KafkaProducerWrapper withSslAvro(String serverPort, String registry) {
        Properties properties = new KafkaClientPropertyBuilder()
                .withServer(serverPort)
                .withProducerSsl()
                .withAvroSerializers(registry)
                .build();
        return new KafkaProducerWrapper(properties);
    }
}
