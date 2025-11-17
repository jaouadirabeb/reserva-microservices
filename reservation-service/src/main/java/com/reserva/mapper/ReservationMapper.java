package com.reserva.mapper;

import com.reserva.dto.CustomerReservationsResponse;
import com.reserva.dto.ReservationResponse;

import com.reserva.entities.Customer;
import com.reserva.entities.Reservation;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ReservationMapper {
    ReservationResponse toReservationResponse(Reservation reservation);
    CustomerReservationsResponse toCustomerResponse(Customer customer);
}
