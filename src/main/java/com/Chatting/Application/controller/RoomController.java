package com.Chatting.Application.controller;


import com.Chatting.Application.dto.RoomDto;
import com.Chatting.Application.entities.Room;
import com.Chatting.Application.repository.RoomRepository;
import com.Chatting.Application.service.RoomService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static reactor.netty.http.HttpConnectionLiveness.log;

@RestController
@RequestMapping("/api/v1/rooms")
@RequiredArgsConstructor
@Slf4j
// this Controller will handle Room Creation
public class RoomController {

    private RoomRepository roomRepository; // using this we can check whether room already there or not
    private RoomService roomService;

    //create room
    @PostMapping
    public ResponseEntity<?>createNewRoom(@RequestBody String roomId){
        log.info("User have created Room with id:"+" "+roomId);
        if(roomRepository.findByRoomId(roomId)!=null){
            // it means room is already present it means it's a bad request
            return ResponseEntity.badRequest().body("Room with Id: "+roomId+" already exists!");
        }
        // elser we will create new room.
        RoomDto room =roomService.createNewRoom(roomId);
        return new ResponseEntity<>(room, HttpStatus.CREATED);
    }
}
