package com.experiment.consumer;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Duration;
import java.util.List;
import java.util.Properties;

public class KafkaConsumerWrapper {

    private final Logger logger = LoggerFactory.getLogger(KafkaConsumerWrapper.class);
    private KafkaConsumer<String, String> kafkaConsumer;

    public KafkaConsumerWrapper(Properties properties) {
        kafkaConsumer = new KafkaConsumer<>(properties);
    }

    public void subscribe(List<String> topics) {
        kafkaConsumer.subscribe(topics);
    }

    public void listen() {
        while(true) {
            ConsumerRecords<String, String> records = kafkaConsumer.poll(Duration.ofMillis(100));

            for(ConsumerRecord record : records) {
                logger.info("Key: " + record.key() + ", Value: " + record.value());
                logger.info("Parition: " + record.partition() + ", Offset: " + record.offset());
            }
        }
    }
}
