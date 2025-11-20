package com.reserva.service;

import com.reserva.events.ReservationCreatedEvent;
import com.reserva.events.RoomAvailabilityResponseEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class HotelRoomAvailabilityListener {
    private final RoomReservationService roomReservationService;

    private final KafkaTemplate<String, Object> kafkaTemplate;
    @KafkaListener(topics = "reservation.events", containerFactory = "reservationCreatedFactory")
    public void handleReservationCreated(ReservationCreatedEvent event) {

        log.info("Hotel received event: {} " , event);

        boolean available = checkRoomAvailability(
                event.hotelId(),
                event.roomId(),
                event.checkin(),
                event.checkout()
        );

        log.info("Reservation {} availability: {}", event.reservationId(), available);

        // publish the availability result
        var result = new RoomAvailabilityResponseEvent(
                UUID.randomUUID().toString(),
                event.reservationId(),
                event.hotelId(),
                available,
                available ? event.roomId() : null,
                available ? null : "Not enough rooms"
        );

        kafkaTemplate.send("hotel.availability.results", event.reservationId().toString(), result);

    }

    private boolean checkRoomAvailability(Long hotelId, Long roomId, LocalDate in, LocalDate out) {
       return  roomReservationService.isAvailable(hotelId, in, out, roomId);
    }
}
