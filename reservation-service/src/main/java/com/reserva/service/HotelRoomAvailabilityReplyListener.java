package com.reserva.service;

import com.reserva.entities.Reservation;
import com.reserva.entities.ReservationStatus;
import com.reserva.events.RoomAvailabilityResponseEvent;
import com.reserva.events.RoomBookedEvent;
import com.reserva.repository.ReservationRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@Slf4j
@RequiredArgsConstructor
public class HotelRoomAvailabilityReplyListener {
    private final KafkaTemplate<String, Object> kafkaTemplate;

    private final ReservationRepository reservationRepository;

@KafkaListener(topics = "hotel.availability.results", containerFactory = "roomAvailabilityResponseFactory")
public void onAvailabilityResult(RoomAvailabilityResponseEvent event) {
    log.info("Received availability event: {}", event);

    var reservationOpt = reservationRepository.findById(event.reservationId());
    if (reservationOpt.isEmpty()) return; // ignore or dead-letter

    var reservation = reservationOpt.get();
    if (event.available()) {
        log.info("Confirmed reservation ");

        reservation.setStatus(ReservationStatus.CONFIRMED);
        reservationRepository.save(reservation);

        //  Send book command to hotel
        sendBookRoomCommand(reservation);

    } else {
        reservation.setStatus(ReservationStatus.CANCELLED);
        reservationRepository.save(reservation);
        log.warn("Reservation {} cancelled: {}", event.reservationId(), event.reason());
    }
}


    private void sendBookRoomCommand(Reservation reservation) {
        var command = new RoomBookedEvent(
                UUID.randomUUID().toString(),
                reservation.getId(),
                reservation.getHotelId(),
                reservation.getRoomId(),
                reservation.getCheckInDate(),
                reservation.getCheckOutDate()
        );

        kafkaTemplate.send("hotel.booking.requests", reservation.getId().toString(), command);
    }
}
