package com.reserva.dto;

import com.reserva.entities.ReservationStatus;
import java.math.BigDecimal;
import java.time.LocalDate;

public record ReservationResponse(

      LocalDate checkInDate,
      LocalDate checkOutDate,
       ReservationStatus status,
       BigDecimal totalPrice
) {}