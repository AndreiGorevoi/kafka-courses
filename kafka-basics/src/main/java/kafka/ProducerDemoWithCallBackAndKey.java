package kafka;

import org.apache.commons.lang3.RandomStringUtils;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.StringSerializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Properties;

public class ProducerDemoWithCallBackAndKey {
    public static final String TOPIC = "java_demo";

    public static void main(String[] args) {
        Logger logger = LoggerFactory.getLogger(ProducerDemoWithCallBackAndKey.class.getName());

        logger.info("I'm a producer");

        Properties properties = new Properties();
        properties.setProperty(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "127.0.0.1:9092");
        properties.setProperty(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        properties.setProperty(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());

        KafkaProducer<String, String> producer = new KafkaProducer<>(properties);

        for (var i = 0; i < 10; i++) {
            ProducerRecord<String, String> record = new ProducerRecord<>(TOPIC, "id_" + 100 + i, RandomStringUtils.randomAlphabetic(6));
            producer.send(record, (metadata, exception) -> {
                if (exception == null) {
                    logger.info("Topic: " + metadata.topic());
                    logger.info("Partition: " + metadata.partition());
                    logger.info("Offset: " + metadata.offset());
                    logger.info("Timestamp: " + metadata.timestamp());
                } else {
                    logger.error(exception.getMessage());
                }
            });
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }


        producer.close();
    }
}
