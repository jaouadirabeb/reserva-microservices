package com.reserva.dto;

import lombok.Builder;

import java.util.List;

@Builder
public record DetailedCustomerReservationsResponse(

        String firstName,
        String lastName,
        String email,
        String phone,
        List<DetailedReservationsResponse> reservations
) {}
