package com.Chatting.Application.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.List;


@Data
@Getter
@Setter
@AllArgsConstructor
public class RoomDto {

    private Long id;
    private String roomId;
    private List<MessagesDto> messages;
}
