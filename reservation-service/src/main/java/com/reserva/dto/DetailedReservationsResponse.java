package com.reserva.dto;

import com.reserva.entities.ReservationStatus;
import lombok.Builder;

import java.math.BigDecimal;
import java.time.LocalDate;

@Builder
public record DetailedReservationsResponse(

       LocalDate checkInDate,
       LocalDate checkOutDate,
       ReservationStatus status,
       BigDecimal totalPrice,
       DetailedRoomResponse roomHotel

) {}