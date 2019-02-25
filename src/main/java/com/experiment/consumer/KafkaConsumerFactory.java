package com.experiment.consumer;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Duration;
import java.util.List;
import java.util.Properties;

public class KafkaConsumerFactory {

    private final Logger logger = LoggerFactory.getLogger(KafkaConsumerFactory.class);
    private KafkaConsumer<String, String> kafkaConsumer;
    private String groupId;

    public KafkaConsumerFactory(String groupId) {
        this.groupId = groupId;

        kafkaConsumer = new KafkaConsumer<>(getProperties());
    }

    public void subscribe(List<String> topics) {
        kafkaConsumer.subscribe(topics);
    }

    private Properties getProperties() {
        Properties properties = new Properties();
        properties.setProperty(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        properties.setProperty(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        properties.setProperty(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        properties.setProperty(ConsumerConfig.GROUP_ID_CONFIG, groupId);
        properties.setProperty(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");
        return properties;
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
