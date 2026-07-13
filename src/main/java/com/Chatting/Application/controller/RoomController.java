package com.Chatting.Application.controller;

import com.Chatting.Application.config.AppConstants;
import com.Chatting.Application.dto.MessagesDto;
import com.Chatting.Application.dto.RoomDto;
import com.Chatting.Application.repository.RoomRepository;
import com.Chatting.Application.service.MessagesService;
import com.Chatting.Application.service.RoomService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/rooms")
@RequiredArgsConstructor
@Slf4j
@CrossOrigin(AppConstants.Frontend_BASE_URL)
// this Controller will handle Room Creation
public class RoomController {

    private final RoomRepository roomRepository; // using this we can check whether room already there or not
    private final RoomService roomService;
    private final MessagesService messagesService;

    // create room
    @PostMapping
    public ResponseEntity<?> createNewRoom(@RequestBody RoomDto roomDto) {
        log.info("User have created Room with id: " + roomDto.getRoomId());

        // Check if room already exists
        if (roomRepository.findByRoomId(roomDto.getRoomId()).isPresent()) {
            // it means room is already present it means it's a bad request
            return ResponseEntity.badRequest().body("Room with Id: " + roomDto.getRoomId() + " already exists!");
        }

        // else we will create new room
        RoomDto createdRoom = roomService.createNewRoom(roomDto);
        return new ResponseEntity<>(createdRoom, HttpStatus.CREATED);
    }

    // get room Api
    @GetMapping("/{roomId}")
    public ResponseEntity<?> joinRoomById(@PathVariable String roomId) {
        log.info("getting room with " + roomId);
        if (roomRepository.findByRoomId(roomId).isEmpty()) {
            return ResponseEntity.badRequest().body("Room with Id: " + roomId + " not found!");
        }
        RoomDto roomDto = roomService.joinRoomById(roomId);
        return ResponseEntity.ok(roomDto);
    }

    // Save message in a room
    @PostMapping("/{roomId}/messages")
    public ResponseEntity<?> saveMessage(
            @PathVariable String roomId,
            @RequestBody MessagesDto messagesDto) {
        try {
            log.info("Saving message in roomId: {}, sender: {}", roomId, messagesDto.getSender());

            // Check if room exists
            if (roomRepository.findByRoomId(roomId).isEmpty()) {
                log.warn("Room with Id: {} not found!", roomId);
                return ResponseEntity.badRequest().body("Room with Id: " + roomId + " not found!");
            }

            // Set roomId in DTO from path variable
            messagesDto.setRoomId(roomId);

            // Save message using service
            MessagesDto savedMessage = messagesService.saveMessage(messagesDto);
            log.info("Message saved successfully with id: {}", savedMessage.getId());
            return new ResponseEntity<>(savedMessage, HttpStatus.CREATED);
        } catch (Exception e) {
            log.error("Error saving message: ", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error saving message: " + e.getMessage());
        }
    }

    // get a message of room with pagination
    @GetMapping("/{roomId}/messages")
    public ResponseEntity<?> getMessages(
            @PathVariable String roomId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        log.info("Getting messages for roomId: {}, page: {}, size: {}", roomId, page, size);

        // Check if room exists
        if (roomRepository.findByRoomId(roomId).isEmpty()) {
            return ResponseEntity.badRequest().body("Room with Id: " + roomId + " not found!");
        }

        // Get paginated messages and extract the list
        Page<MessagesDto> messagesPage = roomService.getMessages(roomId, page, size);
        List<MessagesDto> messagesList = messagesPage.getContent();
        return ResponseEntity.ok(messagesList);
    }
}
