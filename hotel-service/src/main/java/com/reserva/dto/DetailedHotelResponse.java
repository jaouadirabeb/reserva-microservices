package com.reserva.dto;

import java.util.List;

public record DetailedHotelResponse(
        String name,
        String city,
        Double rating,
        List<RoomResponse> rooms
) {}