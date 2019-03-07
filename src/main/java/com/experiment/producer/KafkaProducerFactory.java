package com.experiment.producer;

import org.apache.kafka.clients.CommonClientConfigs;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.config.SslConfigs;
import org.apache.kafka.common.serialization.StringSerializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Properties;

public class KafkaProducerFactory {

    private static final Logger logger = LoggerFactory.getLogger(KafkaProducerFactory.class);

    private KafkaProducer<String, String> producer;

    public KafkaProducerFactory() {
        this.producer = new KafkaProducer<>(getProperties());;
    }

    private Properties getProperties() {
        Properties properties = new Properties();
        properties.setProperty(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9093");
        properties.setProperty(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        properties.setProperty(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());

        //configure the following three settings for SSL Encryption
        properties.setProperty(CommonClientConfigs.SECURITY_PROTOCOL_CONFIG, "SSL");
        properties.setProperty(SslConfigs.SSL_TRUSTSTORE_LOCATION_CONFIG, "/Users/denise/Desktop/Messing_With_Code/experiments/kafka/testing-with-kafka/certs/server.truststore.jks");
        properties.setProperty(SslConfigs.SSL_TRUSTSTORE_PASSWORD_CONFIG,  "password");

        // configure the following three settings for SSL Authentication
        properties.setProperty(SslConfigs.SSL_KEYSTORE_LOCATION_CONFIG, "/Users/denise/Desktop/Messing_With_Code/experiments/kafka/testing-with-kafka/certs/server.keystore.jks");
        properties.setProperty(SslConfigs.SSL_KEYSTORE_PASSWORD_CONFIG, "password");
        properties.setProperty(SslConfigs.SSL_KEY_PASSWORD_CONFIG, "password");
        properties.setProperty("ssl.endpoint.identification.algorithm","");

        return properties;
    }

    public void publishRecord(String topic, String message) {
        ProducerRecord<String, String> record = new ProducerRecord<>(topic, message);
        send(record);
    }

    public void publishRecord(String topic, String message, String key) {
        ProducerRecord<String, String> record = new ProducerRecord<>(topic, key, message);
        logger.info(String.format("Key: %s", key));
        send(record);
    }

    private void send(ProducerRecord record) {
        try {
            producer.send(record, (recordMetadata, e) -> {
                if (e == null) {
                    logger.info("Received new metadata. \n" +
                            "Topic: " + recordMetadata.topic() + "\n" +
                            "Partition: " + recordMetadata.partition() + "\n" +
                            "Offset: " + recordMetadata.offset() + "\n" +
                            "Timestamp: " + recordMetadata.timestamp() +"\n");
                } else {
                    logger.error("Error while producing", e);
                }
            }).get();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void close() {
        producer.flush();
        producer.close();
    }
}
