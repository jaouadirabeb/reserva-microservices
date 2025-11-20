package com.reserva.events;

import java.time.LocalDate;

public record RoomBookedEvent(
        String eventId,
        Long reservationId,
        Long hotelId,
        Long roomId,
        LocalDate checkin,
        LocalDate checkout
) implements ReservationEventBase {}