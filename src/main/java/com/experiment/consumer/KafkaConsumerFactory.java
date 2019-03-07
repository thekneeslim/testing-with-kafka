package com.experiment.consumer;

import com.experiment.utils.KafkaClientPropertyBuilder;
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

    public static KafkaConsumerWrapper withoutSsl(String server, String groupId) {
        Properties properties = new KafkaClientPropertyBuilder()
                .withServer(server)
                .withStringDeserializers()
                .withGroupIdAndEarliestOffset(groupId)
                .build();
        return new KafkaConsumerWrapper(properties);
    }

    public static KafkaConsumerWrapper withSsl(String server, String groupId) {
        Properties properties = new KafkaClientPropertyBuilder()
                .withServer(server)
                .withStringDeserializers()
                .withGroupIdAndEarliestOffset(groupId)
                .withConsumerSsl()
                .build();
        return new KafkaConsumerWrapper(properties);
    }
}
