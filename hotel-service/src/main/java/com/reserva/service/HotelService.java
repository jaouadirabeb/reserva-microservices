package com.reserva.service;

import com.reserva.dto.DetailedHotelResponse;
import com.reserva.entities.Hotel;
import com.reserva.mapper.HotelMapper;
import com.reserva.repository.HotelRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class HotelService {
    private final HotelRepository hotelRepository;
    private final HotelMapper hotelMapper;

    public void saveHotel(Hotel hotel) {
        hotel.getRooms().forEach(hotel::addRoom);
        hotelRepository.save(hotel);
    }

    public List<DetailedHotelResponse> findAllHotels() {
        return hotelRepository.findAll()
                .stream()
                .map(hotelMapper::toHotelResponse)
                .toList();
    }

}
