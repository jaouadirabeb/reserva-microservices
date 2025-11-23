package com.reserva.service;

import com.reserva.events.RoomBookingRollbackEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class RoomBookingRollbackListener {

    private final RoomReservationService roomReservationService;

    @KafkaListener(
            topics = RoomBookingRollbackEvent.TOPIC,
            containerFactory = "rollbackEventFactory"
    )
    public void onRoomBookingRollback(RoomBookingRollbackEvent event) {

        log.warn("Rollback received for reservation {}. Removing room booking…",
                event.reservationId());

        try {
            roomReservationService.removeReservation(event.reservationId());
            log.info("Room booking removed for reservation {}", event.reservationId());
        } catch (Exception ex) {
            log.error("Failed to rollback reservation {} in Hotel MS", event.reservationId(), ex);
        }
    }
}
