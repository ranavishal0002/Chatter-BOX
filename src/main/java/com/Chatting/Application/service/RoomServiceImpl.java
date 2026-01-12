package com.Chatting.Application.service;

import com.Chatting.Application.dto.MessagesDto;
import com.Chatting.Application.dto.RoomDto;
import com.Chatting.Application.entities.Messages;
import com.Chatting.Application.entities.Room;
import com.Chatting.Application.repository.MessagesRepository;
import com.Chatting.Application.repository.RoomRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class RoomServiceImpl implements RoomService {
    private final RoomRepository roomRepository;
    private final MessagesRepository messagesRepository;
    private final ModelMapper modelMapper;

    @Override
    public RoomDto createNewRoom(RoomDto roomDto) {
        log.info("Creating new room with roomId: {}", roomDto.getRoomId());

        // Convert DTO to Entity
        Room room = modelMapper.map(roomDto, Room.class);

        // Save the room
        Room savedRoom = roomRepository.save(room);

        // Convert Entity back to DTO
        RoomDto savedRoomDto = modelMapper.map(savedRoom, RoomDto.class);

        log.info("Room created successfully with id: {}", savedRoom.getId());
        return savedRoomDto;
    }

    @Override
    public RoomDto joinRoomById(String roomId) {
        log.info("Joining room with roomId: {}", roomId);
        
        // Find the room by roomId
        Room room = roomRepository.findByRoomId(roomId)
                .orElseThrow(() -> new RuntimeException("Room with Id: " + roomId + " not found!"));
        
        // Convert Entity to DTO using ModelMapper
        RoomDto roomDto = modelMapper.map(room, RoomDto.class);
        
        log.info("Room found successfully with id: {}", room.getId());
        return roomDto;
    }

    @Override
    public Page<MessagesDto> getMessages(String roomId, int page, int size) {
        log.info("Getting messages for roomId: {}, page: {}, size: {}", roomId, page, size);
        
        // Find the room by roomId
        Room room = roomRepository.findByRoomId(roomId)
                .orElseThrow(() -> new RuntimeException("Room with Id: " + roomId + " not found!"));
        
        // Create Pageable with page and size (default size is 10)
        Pageable pageable = PageRequest.of(page, size);
        
        // Get paginated messages ordered by timestamp descending (newest first)
        Page<Messages> messagesPage = messagesRepository.findByRoomOrderByTimeStampDesc(room, pageable);
        
        // Convert Page<Messages> to Page<MessagesDto>
        Page<MessagesDto> messagesDtoPage = messagesPage.map(message -> modelMapper.map(message, MessagesDto.class));
        
        log.info("Found {} messages for roomId: {}", messagesDtoPage.getTotalElements(), roomId);
        return messagesDtoPage;
    }
}
