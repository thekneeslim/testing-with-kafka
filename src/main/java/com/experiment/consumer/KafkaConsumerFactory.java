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
        properties.setProperty(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9093");
        properties.setProperty(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        properties.setProperty(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        properties.setProperty(ConsumerConfig.GROUP_ID_CONFIG, groupId);
        properties.setProperty(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");

        properties.setProperty("security.protocol","SSL");
        properties.setProperty("ssl.truststore.location","/Users/denise/Desktop/Messing_With_Code/experiments/kafka/testing-with-kafka/certs/client.truststore.jks");
        properties.setProperty("ssl.truststore.password","password");
        properties.setProperty("ssl.keystore.location","/Users/denise/Desktop/Messing_With_Code/experiments/kafka/testing-with-kafka/certs/server.keystore.jks");
        properties.setProperty("ssl.keystore.password","password");
        properties.setProperty("ssl.key.password","password");
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
