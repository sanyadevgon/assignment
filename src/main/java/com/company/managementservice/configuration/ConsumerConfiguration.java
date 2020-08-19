package com.company.managementservice.configuration;

import com.company.managementservice.model.dto.KafkaDto;
import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.listener.ErrorHandler;
import org.springframework.kafka.listener.KafkaListenerErrorHandler;
import org.springframework.kafka.listener.ListenerExecutionFailedException;
import org.springframework.kafka.support.serializer.ErrorHandlingDeserializer;
import org.springframework.kafka.support.serializer.JsonDeserializer;
import org.springframework.kafka.support.serializer.JsonSerializer;
import org.springframework.messaging.Message;

import java.util.HashMap;
import java.util.Map;

@EnableKafka
@Configuration
public class ConsumerConfiguration {

    Logger logger = LoggerFactory.getLogger(ConsumerConfiguration.class);

    @Bean
    public ConsumerFactory<String, KafkaDto> consumerFactory() {
        Map<String, Object> map = new HashMap<>();
        map.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "127.0.0.1:9092");
        map.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        map.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, JsonSerializer.class);
        map.put(ConsumerConfig.GROUP_ID_CONFIG, "group_id");
        ErrorHandlingDeserializer<KafkaDto> errorHandlingDeserializer =
                new ErrorHandlingDeserializer<>(new JsonDeserializer<>(KafkaDto.class));

        return new DefaultKafkaConsumerFactory<>(map, new StringDeserializer(), errorHandlingDeserializer);
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, KafkaDto> concurrentKafkaListenerContainerFactory() {
        ConcurrentKafkaListenerContainerFactory containerFactory = new ConcurrentKafkaListenerContainerFactory();
        containerFactory.setConsumerFactory(consumerFactory());
        containerFactory.setErrorHandler(new KafkaErrorHandler());
        return containerFactory;
    }

    public class KafkaErrorHandler implements ErrorHandler, KafkaListenerErrorHandler {
        @Override
        public Object handleError(Message<?> message, ListenerExecutionFailedException e) {
            logger.info(message.toString());
            return null;
        }

        @Override
        public Object handleError(Message<?> message, ListenerExecutionFailedException ex, Consumer<?, ?> consumer) {
            return null;
        }

        @Override
        public void handle(Exception e, ConsumerRecord<?, ?> consumerRecord) {
        }
    }

}
