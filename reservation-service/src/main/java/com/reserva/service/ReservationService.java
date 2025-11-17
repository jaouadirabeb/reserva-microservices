package com.reserva.service;

import com.reserva.dto.ReservationResponse;
import com.reserva.entities.Reservation;
import com.reserva.mapper.ReservationMapper;
import com.reserva.repository.ReservationRepository;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ReservationService {
    private final ReservationRepository reservationRepository;
    private final ReservationMapper reservationMapper;

    public void saveReservation(Reservation reservation) {
        reservationRepository.save(reservation);
    }
    public List<ReservationResponse> findAllReservations() {

        return reservationRepository.findAll()
                .stream().map(reservationMapper::toReservationResponse).toList();
    }
}
