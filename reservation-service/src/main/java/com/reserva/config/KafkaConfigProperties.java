package com.reserva.config;

import com.reserva.events.RoomAvailabilityResponseEvent;
import com.reserva.events.RoomBookingResultEvent;
import lombok.Getter;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.*;
import org.springframework.kafka.support.serializer.JsonDeserializer;
import org.springframework.kafka.support.serializer.JsonSerializer;

import java.util.HashMap;
import java.util.Map;


@Configuration
@Getter
public class KafkaConfigProperties {

    @Value("${spring.kafka.bootstrap-servers}")
    private String bootstrapServers;



    // -------------------- RoomAvailabilityResponseEvent --------------------
    @Bean
    public ConsumerFactory<String, RoomAvailabilityResponseEvent> roomAvailabilityResponseConsumerFactory() {
        JsonDeserializer<RoomAvailabilityResponseEvent> deserializer =
                new JsonDeserializer<>(RoomAvailabilityResponseEvent.class);
        deserializer.addTrustedPackages("*");
        deserializer.ignoreTypeHeaders();

        return new DefaultKafkaConsumerFactory<>(
                Map.of(
                        ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers,
                        ConsumerConfig.GROUP_ID_CONFIG, "reservation-service-availability-group-test1",
                        ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest"
                ),
                new StringDeserializer(),
                deserializer
        );
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, RoomAvailabilityResponseEvent> roomAvailabilityResponseFactory() {
        ConcurrentKafkaListenerContainerFactory<String, RoomAvailabilityResponseEvent> factory =
                new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(roomAvailabilityResponseConsumerFactory());
        return factory;
    }
    // -------------------- RoomBookingResultEvent --------------------
    @Bean
    public ConsumerFactory<String, RoomBookingResultEvent> roomBookingResultConsumerFactory() {
        JsonDeserializer<RoomBookingResultEvent> deserializer =
                new JsonDeserializer<>(RoomBookingResultEvent.class);
        deserializer.addTrustedPackages("*");
        deserializer.ignoreTypeHeaders();
        return new DefaultKafkaConsumerFactory<>(
                Map.of(
                        ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers,
                        ConsumerConfig.GROUP_ID_CONFIG, "reservation-service-booking-result-group-test1",
                        ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest"
                ),
                new StringDeserializer(),
                deserializer
        );
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, RoomBookingResultEvent> roomBookingResultFactory(
            ConsumerFactory<String, RoomBookingResultEvent> roomBookingResultConsumerFactory) {
        ConcurrentKafkaListenerContainerFactory<String, RoomBookingResultEvent> factory =
                new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(roomBookingResultConsumerFactory);

        return factory;
    }

    @Bean
    public ProducerFactory<String, Object> producerFactory() {
        Map<String, Object> configProps = new HashMap<>();
        configProps.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
        configProps.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        configProps.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class);
        return new DefaultKafkaProducerFactory<>(configProps);
    }

    @Bean
    public KafkaTemplate<String, Object> kafkaTemplate() {
        return new KafkaTemplate<>(producerFactory());
    }

}