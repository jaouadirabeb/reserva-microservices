package com.reserva.controller;

import com.reserva.dto.ReservationResponse;
import com.reserva.entities.Reservation;
import com.reserva.service.ReservationService;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/reservations")
@RequiredArgsConstructor
public class ReservationController {
    private final ReservationService reservationService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void saveReservation(@RequestBody Reservation reservation) {
        reservationService.saveReservation(reservation);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<List<ReservationResponse>> findAllReservations() {
        return ResponseEntity.ok(reservationService.findAllReservations());
    }
}
