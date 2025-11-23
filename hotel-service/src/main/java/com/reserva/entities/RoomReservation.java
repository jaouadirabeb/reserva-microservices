package com.reserva.entities;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RoomReservation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    private Room room;

    private LocalDate checkin;

    private LocalDate checkout;

    // From reservation-service
    private Long reservationId;

    @Enumerated(EnumType.STRING)
    private RoomReservationStatus status ;

}