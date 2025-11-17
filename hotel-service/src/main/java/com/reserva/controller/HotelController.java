package com.reserva.controller;

import com.reserva.dto.DetailedHotelResponse;
import com.reserva.entities.Hotel;
import com.reserva.service.HotelService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/hotels")
@RequiredArgsConstructor
public class HotelController {
    private  final HotelService hotelService;

  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
    public void saveHotel(@RequestBody Hotel hotel) {
      hotelService.saveHotel(hotel);
  }

  @GetMapping
  @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<List<DetailedHotelResponse>> findAllHotels() {
      return ResponseEntity.ok(hotelService.findAllHotels());
  }
}
