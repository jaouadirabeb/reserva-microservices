package com.reserva.service;

import com.reserva.dto.DetailedRoomResponse;
import com.reserva.entities.Room;
import com.reserva.mapper.RoomMapper;
import com.reserva.repository.RoomRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RoomService {

    private final RoomRepository roomRepository;
    private final RoomMapper roomMapper;

    public DetailedRoomResponse findDetailedRoomById(long idRoom) {
        Room room = roomRepository.findById(idRoom)
                .orElseThrow(() -> new RuntimeException("Room not found"));
        return roomMapper.toDetailedRoomResponse(room);
    }


}
