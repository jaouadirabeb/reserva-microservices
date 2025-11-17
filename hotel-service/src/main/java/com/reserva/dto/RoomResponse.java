package com.reserva.dto;

import com.reserva.entities.RoomStatus;
import com.reserva.entities.RoomType;

import java.math.BigDecimal;

public record RoomResponse(
        String roomNumber,
        RoomType type,
        RoomStatus status,
        BigDecimal pricePerNight,
        Integer capacity
) {}
