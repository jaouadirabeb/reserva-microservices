package com.reserva.events;

public record RoomBookingRollbackEvent(
        Long reservationId,
        String reason
) {
    public static final String TOPIC = "reservation.rollback.room";
}