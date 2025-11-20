package com.reserva.repository;

import com.reserva.entities.RoomReservation;
import com.reserva.entities.RoomReservationStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;

public interface RoomReservationRepository extends JpaRepository<RoomReservation, Long> {

    @Query("""
        SELECT COUNT(rr) = 0
        FROM RoomReservation rr
        WHERE rr.room.hotel.id = :hotelId
          AND rr.room.id = :roomId
          AND rr.checkin < :checkout
          AND rr.checkout > :checkin
          AND rr.status = :status
       """)
    boolean isRoomAvailable(@Param("hotelId") Long hotelId,
                            @Param("checkin") LocalDate checkin,
                            @Param("checkout") LocalDate checkout,
                            @Param("roomId") Long roomId,
                            @Param("status") RoomReservationStatus status);
}
