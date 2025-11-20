package com.reserva.service;

import com.reserva.dto.ReservationResponse;
import com.reserva.entities.Reservation;
import com.reserva.entities.ReservationStatus;
import com.reserva.mapper.ReservationMapper;
import com.reserva.repository.ReservationRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class ReservationService {
    private final ReservationRepository reservationRepository;
    private final ReservationMapper reservationMapper;
    private final ReservationCreatedEventPublisher reservationCreatedEventPublisher;

    public void saveReservation(Reservation reservation) {

        validateDates(reservation.getCheckInDate(), reservation.getCheckOutDate());

        // 1️- Save with initial PENDING status
        reservation.setStatus(ReservationStatus.PENDING);
        Reservation saved = reservationRepository.save(reservation);
         log.info("saved {}", saved);

         // 2️- Publish an event to ask Hotel service to check availability
        reservationCreatedEventPublisher.publishReservationCreated(saved);
    }

    public List<ReservationResponse> findAllReservations() {

        return reservationRepository.findAll()
                .stream().map(reservationMapper::toReservationResponse).toList();
    }

    private void validateDates(LocalDate checkin, LocalDate checkout) {

        if (checkin == null || checkout == null) {
            throw new IllegalArgumentException("Check-in and check-out dates are required");
        }

        if (!checkin.isBefore(checkout)) {
            throw new IllegalArgumentException("Check-in date must be before check-out date");
        }

        if (checkin.isBefore(LocalDate.now())) {
            throw new IllegalArgumentException("Check-in date cannot be in the past");
        }
    }

}


