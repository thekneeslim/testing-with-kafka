package com.experiment.utils;

import com.experiment.producer.KafkaProducerFactory;
import org.apache.kafka.clients.CommonClientConfigs;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.config.SslConfigs;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.kafka.common.serialization.StringSerializer;

import java.util.Properties;

public class KafkaClientPropertyBuilder {

    private Properties properties;

    public KafkaClientPropertyBuilder() {
        properties = new Properties();
    }

    public KafkaClientPropertyBuilder withServer(String serverPort) {
        properties.setProperty(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, serverPort);
        return this;
    }

    public KafkaClientPropertyBuilder withStringSerializer() {
        properties.setProperty(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        properties.setProperty(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        return this;
    }

    public KafkaClientPropertyBuilder withProducerSsl() {
        //configure the following three settings for SSL Encryption
        properties.setProperty(CommonClientConfigs.SECURITY_PROTOCOL_CONFIG, "SSL");
        properties.setProperty(SslConfigs.SSL_TRUSTSTORE_LOCATION_CONFIG, "./certs/server.truststore.jks");
        properties.setProperty(SslConfigs.SSL_TRUSTSTORE_PASSWORD_CONFIG,  "password");

        // configure the following three settings for SSL Authentication
        properties.setProperty(SslConfigs.SSL_KEYSTORE_LOCATION_CONFIG, "./certs/server.keystore.jks");
        properties.setProperty(SslConfigs.SSL_KEYSTORE_PASSWORD_CONFIG, "password");
        properties.setProperty(SslConfigs.SSL_KEY_PASSWORD_CONFIG, "password");
        properties.setProperty("ssl.endpoint.identification.algorithm","");
        return this;
    }

    public KafkaClientPropertyBuilder withStringDeserializers() {
        properties.setProperty(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        properties.setProperty(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        return this;
    }

    public KafkaClientPropertyBuilder withGroupIdAndEarliestOffset(String groupId) {
        properties.setProperty(ConsumerConfig.GROUP_ID_CONFIG, groupId);
        properties.setProperty(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");
        return this;
    }

    public KafkaClientPropertyBuilder withConsumerSsl() {
        //configure the following three settings for SSL Encryption
        properties.setProperty(CommonClientConfigs.SECURITY_PROTOCOL_CONFIG, "SSL");
        properties.setProperty(SslConfigs.SSL_TRUSTSTORE_LOCATION_CONFIG, "./certs/client.truststore.jks");
        properties.setProperty(SslConfigs.SSL_TRUSTSTORE_PASSWORD_CONFIG,  "password");

        // configure the following three settings for SSL Authentication
        properties.setProperty(SslConfigs.SSL_KEYSTORE_LOCATION_CONFIG, "./certs/server.keystore.jks");
        properties.setProperty(SslConfigs.SSL_KEYSTORE_PASSWORD_CONFIG, "password");
        properties.setProperty(SslConfigs.SSL_KEY_PASSWORD_CONFIG, "password");
        properties.setProperty("ssl.endpoint.identification.algorithm","");

        return this;
    }

    public Properties build() {
        return properties;
    }
}
