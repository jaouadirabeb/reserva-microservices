package com.reserva.controller;

import com.reserva.dto.DetailedRoomResponse;
import com.reserva.service.RoomService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("api/v1/rooms")
@RequiredArgsConstructor
public class RoomController {
    private final RoomService roomService;


    @GetMapping(value="/with-hotel/{id-room}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<DetailedRoomResponse> findDetailedRoomById(@PathVariable("id-room") Long idRoom) {
        return ResponseEntity.ok(roomService.findDetailedRoomById(idRoom));
    }


}
