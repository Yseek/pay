package com.pay.user.config;

import com.pay.common.event.UserDelEvent;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.support.serializer.JsonSerializer;

import java.util.HashMap;

@Configuration
public class KafkaProducerConfig {

    @Bean
    public ProducerFactory<String, UserDelEvent> producerFactory() {
        HashMap<String, Object> cofig = new HashMap<>();
        cofig.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "kafka:9092");
        cofig.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        cofig.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class);
        return new DefaultKafkaProducerFactory<>(cofig);
    }

    @Bean
    public KafkaTemplate<String, UserDelEvent> kafkaTemplate() {
        return new KafkaTemplate<>(producerFactory());
    }
}
