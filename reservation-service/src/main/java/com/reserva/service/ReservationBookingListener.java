package com.reserva.service;

import com.reserva.events.RoomBookingRollbackEvent;
import com.reserva.entities.ReservationStatus;
import com.reserva.events.RoomBookingResultEvent;
import com.reserva.repository.ReservationRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class ReservationBookingListener {

    private final ReservationRepository reservationRepository;
    private final EventPublisher eventPublisher;

    @KafkaListener(topics = "reservation.booking.result", containerFactory = "roomBookingResultFactory")
    public void onRoomBookingResult(RoomBookingResultEvent event) {
       try{ reservationRepository.findById(event.reservationId()).ifPresent(reservation -> {
            if (event.booked()) {
                reservation.setStatus(ReservationStatus.BOOKED);
                reservation.setTotalPrice(event.totalPrice());
                log.info("Reservation {} status updated to BOOKED", event.reservationId());
            } else {
                reservation.setStatus(ReservationStatus.CANCELLED);
                log.warn("Reservation {} booking failed, status set to CANCELLED", event.reservationId());
            }
            reservationRepository.save(reservation);
        });
       } catch(Exception e){
           log.error("Failed to update reservation {}. Sending rollback event...", event.reservationId(), e);

           // 🔥 Publish rollback event to cancel room booking in Hotel MS
           RoomBookingRollbackEvent rollbackEvent = new RoomBookingRollbackEvent(
                   event.reservationId(),
                   "Reservation MS failed to save status"
           );

           eventPublisher.publish(RoomBookingRollbackEvent.TOPIC, rollbackEvent.reservationId().toString(), rollbackEvent);
       }
    }
}
