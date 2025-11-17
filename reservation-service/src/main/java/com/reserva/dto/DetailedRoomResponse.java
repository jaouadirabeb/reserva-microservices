package com.reserva.dto;

import java.math.BigDecimal;

public record DetailedRoomResponse(

        String roomNumber,
        RoomType type,
        RoomStatus status,
        BigDecimal pricePerNight,
        Integer capacity,
        HotelResponse hotel
) {}
