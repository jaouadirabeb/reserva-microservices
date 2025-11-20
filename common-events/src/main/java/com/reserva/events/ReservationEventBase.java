package com.reserva.events;

public interface ReservationEventBase {
    Long reservationId();
    Long hotelId();
    Long roomId();
}