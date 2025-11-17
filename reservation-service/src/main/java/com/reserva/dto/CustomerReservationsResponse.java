package com.reserva.dto;

import java.util.List;

public record CustomerReservationsResponse(
        String firstName,
        String lastName,
        String email,
        String phone,
        List<ReservationResponse> reservations
) {}
