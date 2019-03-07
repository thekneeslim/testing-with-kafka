package com.experiment.producer;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Properties;

public class KafkaProducerWrapper {

    private static final Logger logger = LoggerFactory.getLogger(KafkaProducerWrapper.class);
    private KafkaProducer<String, String> producer;

    public KafkaProducerWrapper(Properties properties) {
        this.producer = new KafkaProducer<>(properties);;
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
