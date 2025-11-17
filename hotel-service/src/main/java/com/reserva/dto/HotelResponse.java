package com.reserva.dto;

import java.util.List;

public record HotelResponse(

        String name,
        String city,
        Double rating

) {}