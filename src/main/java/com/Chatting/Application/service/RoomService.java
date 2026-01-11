package com.Chatting.Application.service;


import com.Chatting.Application.dto.RoomDto;
import org.springframework.stereotype.Service;

@Service
public interface RoomService {
    RoomDto createNewRoom(RoomDto roomDto);
}
