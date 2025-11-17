package com.reserva.mapper;

import com.reserva.dto.DetailedHotelResponse;
import com.reserva.dto.DetailedRoomResponse;
import com.reserva.dto.RoomResponse;
import com.reserva.entities.Hotel;
import com.reserva.entities.Room;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface HotelMapper {
    DetailedHotelResponse toHotelResponse(Hotel hotel);

}
