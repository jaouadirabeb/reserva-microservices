package com.reserva.mapper;

import com.reserva.dto.DetailedHotelResponse;
import com.reserva.entities.Hotel;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface HotelMapper {
    DetailedHotelResponse toHotelResponse(Hotel hotel);

}
