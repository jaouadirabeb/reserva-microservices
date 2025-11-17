package com.reserva.dto;

import com.reserva.entities.RoomStatus;
import com.reserva.entities.RoomType;

import java.math.BigDecimal;

public record DetailedRoomResponse(

        String roomNumber,
        RoomType type,
        RoomStatus status,
        BigDecimal pricePerNight,
        Integer capacity,
        HotelResponse hotel
) {}
