package com.reserva.config;

import com.reserva.events.ReservationCreatedEvent;
import com.reserva.events.RoomBookedEvent;
import lombok.Getter;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.support.serializer.JsonDeserializer;

import java.util.Map;

@Configuration
@Getter
public class KafkaConfigProperties {

    @Value("${spring.kafka.bootstrap-servers}")
    private String bootstrapServers;

    // -------------------- ReservationCreatedEvent --------------------
    @Bean
    public ConsumerFactory<String, ReservationCreatedEvent> reservationCreatedConsumerFactory() {
        JsonDeserializer<ReservationCreatedEvent> deserializer =
                new JsonDeserializer<>(ReservationCreatedEvent.class);
        deserializer.addTrustedPackages("*");
        deserializer.ignoreTypeHeaders();

        return new DefaultKafkaConsumerFactory<>(
                Map.of(
                        ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers,
                        ConsumerConfig.GROUP_ID_CONFIG, "hotel-service-reservation-group_test",
                        ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest"
                ),
                new StringDeserializer(),
                deserializer
        );
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, ReservationCreatedEvent> reservationCreatedFactory() {
        ConcurrentKafkaListenerContainerFactory<String, ReservationCreatedEvent> factory =
                new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(reservationCreatedConsumerFactory());
        return factory;
    }

    // -------------------- RoomBookedEvent --------------------
    @Bean
    public ConsumerFactory<String, RoomBookedEvent> roomBookedConsumerFactory(KafkaConfigProperties props) {
        JsonDeserializer<RoomBookedEvent> deserializer = new JsonDeserializer<>(RoomBookedEvent.class);
        deserializer.addTrustedPackages("*");
        deserializer.ignoreTypeHeaders();
        return new DefaultKafkaConsumerFactory<>(
                Map.of(
                        ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, props.getBootstrapServers(),
                        ConsumerConfig.GROUP_ID_CONFIG, "hotel-service-booking-group-test",
                        ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest"
                ),
                new StringDeserializer(),
                deserializer
        );
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, RoomBookedEvent> roomBookedFactory(
            ConsumerFactory<String, RoomBookedEvent> roomBookedConsumerFactory) {
        ConcurrentKafkaListenerContainerFactory<String, RoomBookedEvent> factory =
                new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(roomBookedConsumerFactory);

        return factory;
    }
}