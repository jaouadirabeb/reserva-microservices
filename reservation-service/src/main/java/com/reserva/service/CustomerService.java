package com.reserva.service;

import com.reserva.client.RoomClient;
import com.reserva.dto.CustomerReservationsResponse;
import com.reserva.dto.DetailedCustomerReservationsResponse;
import com.reserva.dto.DetailedReservationsResponse;
import com.reserva.entities.Customer;
import com.reserva.mapper.ReservationMapper;
import com.reserva.repository.CustomerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CustomerService {
    private final CustomerRepository customerRepository;
    private final  ReservationMapper  reservationMapper;
   private final RoomClient roomClient;

    public void saveCustomer(Customer customer) {
            customer.getReservations().forEach(r -> r.setCustomer(customer));
            customerRepository.save(customer);
    }
    public List<CustomerReservationsResponse> findAllCustomer() {
        return customerRepository.findAll()
                .stream()
                .map(reservationMapper::toCustomerResponse)
                .toList();
    }
    public CustomerReservationsResponse findReservationsByCustomerId(Long customerId) {
        Customer customer = customerRepository.findById(customerId)
                .orElseThrow(() -> new RuntimeException("Customer not found"));
        return reservationMapper.toCustomerResponse(customer);
    }

    public DetailedCustomerReservationsResponse findReservationsWithHotelByCustomerId(Long customerId) {
        var customer = customerRepository.findById(customerId)
                .orElse(
                        Customer.builder()
                                .firstName("NotFound")
                                .lastName("NotFound")
                                .build()
                );

        List<DetailedReservationsResponse> detailedReservations = customer.getReservations()
                .stream()
                .map(reservation -> {
                            var room = Optional.ofNullable(roomClient.findDetailedRoomById(reservation.getRoomId()))
                            .orElseThrow(() -> new RuntimeException("Room not found"));

                            return DetailedReservationsResponse.builder()
                                    .roomHotel(room)
                                    .checkInDate(reservation.getCheckInDate())
                                    .checkOutDate(reservation.getCheckOutDate())
                                    .status(reservation.getStatus())
                                    .totalPrice(reservation.getTotalPrice())
                                    .build();

                })
                .toList();

        return DetailedCustomerReservationsResponse.builder()
                .firstName(customer.getFirstName())
                .lastName(customer.getLastName())
                .email(customer.getEmail())
                .phone(customer.getPhone())
                .reservations(detailedReservations)
                .build();

    }
}
