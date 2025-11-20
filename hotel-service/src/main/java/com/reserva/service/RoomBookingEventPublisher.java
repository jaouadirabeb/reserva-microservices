package com.reserva.service;

import com.reserva.dto.RoomBookingDomainEvent;
import com.reserva.events.RoomBookingResultEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionalEventListener;

@Component
@RequiredArgsConstructor
@Slf4j
public class RoomBookingEventPublisher {

    private final KafkaTemplate<String, Object> kafkaTemplate;

    @TransactionalEventListener
    public void sendRoomBookingResult(RoomBookingDomainEvent event) {
        log.info("Sending booking result for reservation {}", event.reservationId());

        kafkaTemplate.send("reservation.booking.result",
                event.reservationId().toString(),
                new RoomBookingResultEvent(event.reservationId(), event.totalPrice(), event.booked()));
    }
}
