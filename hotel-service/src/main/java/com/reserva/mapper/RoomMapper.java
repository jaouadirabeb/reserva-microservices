package com.reserva.mapper;

import com.reserva.dto.DetailedRoomResponse;
import com.reserva.dto.RoomResponse;
import com.reserva.entities.Room;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface RoomMapper {
    RoomResponse toRoomResponse(Room room);
    DetailedRoomResponse toDetailedRoomResponse(Room room);
}
