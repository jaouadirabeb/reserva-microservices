package com.reserva.Service;

import com.reserva.config.KafkaConfigProperties;
import com.reserva.events.RoomBookingRollbackEvent;
import com.reserva.service.RoomBookingRollbackListener;
import com.reserva.service.RoomReservationService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.test.context.EmbeddedKafka;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import java.time.Duration;

import static org.awaitility.Awaitility.await;
import static org.mockito.Mockito.*;

@SpringBootTest(classes = {RoomBookingRollbackListener.class, KafkaConfigProperties.class},
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
@EmbeddedKafka(partitions = 1, topics = {RoomBookingRollbackEvent.TOPIC})
public class RoomBookingRollbackListenerTest {

    @Autowired
    KafkaTemplate<String, Object> kafkaTemplate;

    @MockitoBean
    RoomReservationService roomReservationService;

    @Autowired
    RoomBookingRollbackListener rollbackListener;


    @Test
    void rollbackEventIsConsumed_andReservationRemoved() {
        // given
        RoomBookingRollbackEvent event = new RoomBookingRollbackEvent(
                10L,
                "Test rollback reason"
        );

        // when
        kafkaTemplate.send(RoomBookingRollbackEvent.TOPIC, "10", event);

        // then - wait for async listener to process
        await().atMost(Duration.ofSeconds(5))
                .untilAsserted(() ->
                        verify(roomReservationService, times(1))
                                .removeReservation(10L)
                );
    }
}
