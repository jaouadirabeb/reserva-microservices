package com.reserva.dto;

import java.math.BigDecimal;

//A domain event for DB commit
public record RoomBookingDomainEvent(
        Long reservationId,
        BigDecimal totalPrice,
        boolean booked
) {}

