package com.Chatting.Application.dto;

import lombok.*;

import java.util.List;


@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RoomDto {

    private Long id;
    private String roomId;
    private List<MessagesDto> messages;
}
