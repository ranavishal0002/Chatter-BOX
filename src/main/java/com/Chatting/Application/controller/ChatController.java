package com.Chatting.Application.controller;

import com.Chatting.Application.dto.MessagesDto;
import com.Chatting.Application.entities.Room;
import com.Chatting.Application.repository.RoomRepository;
import com.Chatting.Application.service.MessagesService;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;

import static reactor.netty.http.HttpConnectionLiveness.log;
@RestController
@RequiredArgsConstructor
@CrossOrigin("http://localhost:5173")
// This Controller will handle chats
public class ChatController {

    private final RoomRepository roomRepository;
    private final MessagesService messagesService;

    // Sending and Recieving Messages
    @MessageMapping("/sendMessages/{roomId}") // msg sent here /chat/sendMesages/{roomId}
    @SendTo("/topic/room/{roomId}") //Subscribe
    public MessagesDto sendMessage(@Payload MessagesDto messagesDto) {
        log.info("Received Message from Room with Id:" + messagesDto.getRoomId());
        Room room = roomRepository.findByRoomId(messagesDto.getRoomId())
                .orElseThrow(() -> new RuntimeException("Room not found"));

        // save Message
        MessagesDto savedMessage = messagesService.saveMessage(messagesDto);
        return savedMessage;
    }
}
