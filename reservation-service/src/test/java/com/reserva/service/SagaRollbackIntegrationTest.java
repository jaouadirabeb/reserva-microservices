package com.reserva.service;

import com.reserva.entities.Reservation;
import com.reserva.events.RoomBookingResultEvent;
import com.reserva.events.RoomBookingRollbackEvent;
import com.reserva.repository.CustomerRepository;
import com.reserva.repository.ReservationRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.test.context.EmbeddedKafka;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import java.math.BigDecimal;
import java.time.Duration;
import java.util.Optional;

import static org.awaitility.Awaitility.await;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@SpringBootTest(
        properties = {
                "eureka.client.enabled=false",
                "spring.cloud.config.enabled=false",
                "application.config.room-url=localhost:9090"
        }
)
@EnableAutoConfiguration(exclude = {
        DataSourceAutoConfiguration.class,
        HibernateJpaAutoConfiguration.class
})
@EmbeddedKafka(partitions = 1, topics = {
        "reservation.booking.result",
        "reservation.rollback.room"
})
public class SagaRollbackIntegrationTest {


    @Autowired
    KafkaTemplate<String, Object> kafkaTemplate;

    @MockitoBean
    CustomerRepository customerRepository;

    @Autowired
    @MockitoBean
    ReservationRepository reservationRepository;

    @MockitoBean
    EventPublisher eventPublisher;

    @Test
    void testSendEvent() {
        RoomBookingResultEvent event = new RoomBookingResultEvent(
                10L,  BigDecimal.valueOf(200),true);

        kafkaTemplate.send("reservation.booking.result", "10", event);
    }


    @Test
    void rollbackIsPublishedWhenSaveFails() throws Exception {
        Reservation mockReservation = new Reservation();
        mockReservation.setId(10L);

        when(reservationRepository.findById(10L))
                .thenReturn(Optional.of(mockReservation));
        // Force failure in save()
        doThrow(new RuntimeException("DB error"))
                .when(reservationRepository).save(any());

        RoomBookingResultEvent event = new RoomBookingResultEvent(
                10L,
                new BigDecimal("200.75"),
                true
        );

        // Publish event
        kafkaTemplate.send("reservation.booking.result", "10", event);

        await().atMost(Duration.ofSeconds(3))
                .untilAsserted(() ->
                        verify(eventPublisher).publish(
                                eq(RoomBookingRollbackEvent.TOPIC),
                                eq("10"),
                                any(RoomBookingRollbackEvent.class)
                        )
                );
    }
}
