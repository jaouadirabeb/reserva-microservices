package com.reserva.client;

import com.reserva.config.FeignClientConfig;
import com.reserva.dto.DetailedRoomResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseStatus;

@FeignClient(name = "room-client",url = "${application.config.room-url}",     configuration = FeignClientConfig.class)
public interface RoomClient {
    @GetMapping("/with-hotel/{id-room}")
    @ResponseStatus(HttpStatus.OK)
    DetailedRoomResponse findDetailedRoomById(@PathVariable("id-room") Long idRoom);
    }

