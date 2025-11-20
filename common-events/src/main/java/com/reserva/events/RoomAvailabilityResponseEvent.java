package com.reserva.events;

public record RoomAvailabilityResponseEvent(
        String eventId,
        Long reservationId,
        Long hotelId,
        boolean available,
        Long roomId,
        String reason // optional explanation if not available
) implements ReservationEventBase {}