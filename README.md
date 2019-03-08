## Starting Up Kafka

Start Zookeeper
```
zookeeper-server-start /path/to/zookeeper.properties
```

Start Kafka
```
kafka-server-start /path/to/server.properties
```

Start Mirror
```
/bin/kafka-mirror-maker --consumer.config /path/to/consumer.properties --producer.config /path/to/producer.properties --whitelist secure_topic
```

Start Consumer with SSL from beginning & Group
```
./bin/kafka-console-consumer --bootstrap-server 127.0.0.1:9098 --topic topic_name --consumer.config /path/to/consumer.properties --from-beginnin --group my_group
```

Start Producer with SSL
```
./bin/kafka-console-producer --broker-list 127.0.0.1:9098 --topic my_secured_topic --producer.config /path/to/producer.properties
```

Creating a topic
```
kafka-topics --zookeeper 127.0.0.1:2181 --topic topic_name --create --partitions 3 --replication-factor 1
```

Debugging
```
export KAFKA_OPTS="-Dlog4j.configuration=file:/path/to/etc/kafka/log4j.properties"
```

### Customize SSL Principal Reader
Jar has been included in `Sample Configurations` for your reference and should be placed in `/confluent-4.1.1/share/java/kafka`.


### Avro

Avro requires these JVM & environment variables to start a producer/consumer with SSL

```
export SCHEMA_REGISTRY_OPTS="-Djavax.net.ssl.keyStore=/path/to/certs/server.keystore.jks -Djavax.net.ssl.trustStore=/path/to/certs/server.truststore.jks -Djavax.net.ssl.keyStorePassword=password -Djavax.net.ssl.trustStorePassword=password"

export KAFKA_OPTS="-Djavax.net.ssl.keyStore=/path/to/certs/server.keystore.jks -Djavax.net.ssl.trustStore=/path/to/certs/server.truststore.jks -Djavax.net.ssl.keyStorePassword=password -Djavax.net.ssl.trustStorePassword=password"
```

Schema Registry Start
```
./bin/schema-registry-start ./etc/schema-registry/schema-registry.properties
```

Avro Consumer
```
./bin/kafka-avro-console-consumer --bootstrap-server 127.0.0.1:9098 --topic avro_topic
```

Avro Producer
```
./bin/kafka-avro-console-producer --broker-list 127.0.0.1:9098 --topic my_secured_avro_topic --property value.schema='{"type":"record","name":"myrecord","fields":[{"name":"f1","type":"string"}]}' --producer.config ./etc/kafka/avro-producer.properties
```
