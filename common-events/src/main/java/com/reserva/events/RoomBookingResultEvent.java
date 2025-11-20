package com.reserva.events;

import java.math.BigDecimal;

public record RoomBookingResultEvent(
        Long reservationId,
        BigDecimal totalPrice,
        boolean booked
) {}