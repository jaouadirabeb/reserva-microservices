package com.reserva.controller;

import com.reserva.dto.CustomerReservationsResponse;
import com.reserva.dto.DetailedCustomerReservationsResponse;
import com.reserva.entities.Customer;
import com.reserva.service.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/customers")
@RequiredArgsConstructor
public class CustomerController {
    private final CustomerService customerService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void saveCustomer(@RequestBody Customer customer) {
        customerService.saveCustomer(customer);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<List<CustomerReservationsResponse>> findAllCustomers() {
        return ResponseEntity.ok(customerService.findAllCustomer());
    }

    @GetMapping
    @RequestMapping("/reservations/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<CustomerReservationsResponse> findCustomerReservations(@PathVariable("id")  Long id) {
        return ResponseEntity.ok(customerService.findReservationsByCustomerId(id));
    }

    @GetMapping
    @RequestMapping("/reservations/with-hotel/{id-customer}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<DetailedCustomerReservationsResponse> findCustomerReservationsWithHotelDetails(@PathVariable("id-customer")  Long customerId) {
        return ResponseEntity.ok(customerService.findReservationsWithHotelByCustomerId(customerId));
    }
}
