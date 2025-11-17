package com.reserva.repository;

import com.reserva.entities.Room;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoomRepository extends JpaRepository<Room, Long> {
    Object findRoomById(Long id);
}
