package com.reserva.events;

import java.time.LocalDate;

public record ReservationCreatedEvent(
        String eventId,
        Long reservationId,
        Long customerId,
        Long hotelId,
        Long roomId,
        LocalDate checkin,
        LocalDate checkout
) implements ReservationEventBase {}