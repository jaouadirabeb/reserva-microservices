package com.reserva.service;

import com.reserva.events.RoomBookedEvent;
import com.reserva.events.RoomBookingResultEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;


@Service
@RequiredArgsConstructor
@Slf4j
public class HotelRoomBookingListener {
    private final KafkaTemplate<String, Object> kafkaTemplate;

    private final RoomReservationService roomReservationService;


    @KafkaListener(topics = "hotel.booking.requests", containerFactory = "roomBookedFactory")
    public void bookRoomWithKafka(RoomBookedEvent event) {
        log.info("Booking room for reservation {}", event.reservationId());

        try {
             roomReservationService.bookRoom(
                    event.reservationId(),
                    event.hotelId(),
                    event.checkin(),
                    event.checkout(),
                    event.roomId()
            );
        } catch (Exception e) {
            log.error("Error booking room for reservation {}", event.reservationId(), e);

            // Only send failed event immediately if the service didn't publish any domain event
            var failedEvent = new RoomBookingResultEvent(event.reservationId(), BigDecimal.ZERO, false);
            kafkaTemplate.send("reservation.booking.result", event.reservationId().toString(), failedEvent);

        }
    }



}
