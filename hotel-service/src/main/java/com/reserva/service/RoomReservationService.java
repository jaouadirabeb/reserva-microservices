package com.reserva.service;

import com.reserva.dto.RoomBookingDomainEvent;
import com.reserva.entities.Room;
import com.reserva.entities.RoomReservation;
import com.reserva.entities.RoomReservationStatus;
import com.reserva.repository.RoomRepository;
import com.reserva.repository.RoomReservationRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

@Service
@RequiredArgsConstructor
@Slf4j
public class RoomReservationService {

    private final RoomReservationRepository roomReservationRepository;
    private final RoomRepository roomRepository;
    private final ApplicationEventPublisher eventPublisher;

    @Transactional(readOnly = true)
    public boolean isAvailable(Long hotelId,
                               LocalDate checkin,
                               LocalDate checkout,
                               Long roomId) {

        return roomReservationRepository.isRoomAvailable(
                hotelId, checkin, checkout, roomId, RoomReservationStatus.CONFIRMED
        );
    }

    @Transactional
    public void bookRoom(Long reservationId,
                         Long hotelId,
                         LocalDate checkin,
                         LocalDate checkout,
                         Long roomId) {
        try{
            Room room = roomRepository.findById(roomId)
                    .orElseThrow(() -> new RuntimeException("Room not found"));

            RoomReservation rr = RoomReservation.builder()
                        .room(room)
                        .checkin(checkin)
                        .checkout(checkout)
                        .reservationId(reservationId)
                        .status(RoomReservationStatus.CONFIRMED)
                        .build();
            roomReservationRepository.save(rr);
            var totalPrice = calculateTotalPrice(checkin,checkout,room.getPricePerNight());

            // Publish Spring domain event (after transaction commits)
            eventPublisher.publishEvent(new RoomBookingDomainEvent(reservationId, totalPrice, true));

        } catch (Exception e) {
         log.error("Failed to book room {} for reservation {}", roomId, reservationId, e);
        }

    }

    public BigDecimal calculateTotalPrice(LocalDate checkin, LocalDate checkout, BigDecimal pricePerNight) {
        long nights = ChronoUnit.DAYS.between(checkin, checkout);

        if (nights <= 0) {
            throw new IllegalArgumentException("Checkout date must be after checkin date");
        }

        return pricePerNight.multiply(BigDecimal.valueOf(nights));
    }
}
