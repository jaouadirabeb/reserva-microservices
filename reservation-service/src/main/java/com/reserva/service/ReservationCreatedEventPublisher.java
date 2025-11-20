package com.reserva.service;

import com.reserva.entities.Reservation;
import com.reserva.events.ReservationCreatedEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class ReservationCreatedEventPublisher {
    private final KafkaTemplate<String, Object> kafkaTemplate;

    public void publishReservationCreated(Reservation reservation) {
        var event = new ReservationCreatedEvent(
                UUID.randomUUID().toString(),
                reservation.getId(),
                reservation.getCustomer().getId(),
                reservation.getHotelId(),
                reservation.getRoomId(),
                reservation.getCheckInDate(),
                reservation.getCheckOutDate()
        );
        log.info("Reservation published {} ", event);

        kafkaTemplate.send("reservation.events", reservation.getId().toString(), event);
    }
}
