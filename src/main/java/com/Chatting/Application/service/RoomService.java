package com.Chatting.Application.service;


import com.Chatting.Application.dto.MessagesDto;
import com.Chatting.Application.dto.RoomDto;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

@Service
public interface RoomService {
    RoomDto createNewRoom(RoomDto roomDto);
    RoomDto joinRoomById(String roomId);
    Page<MessagesDto> getMessages(String roomId, int page, int size);
}
