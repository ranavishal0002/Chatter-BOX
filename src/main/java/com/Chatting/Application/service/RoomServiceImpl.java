package com.Chatting.Application.service;



import com.Chatting.Application.repository.RoomRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class RoomServiceImpl {
    private final RoomRepository roomRepository;
    private final ModelMapper modelMapper;
}
