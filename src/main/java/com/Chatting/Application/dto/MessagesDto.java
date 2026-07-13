package com.Chatting.Application.dto;


import lombok.*;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MessagesDto {

    private Long id;
    private String roomId;
    private String sender;
    private String content;
    private LocalDateTime timeStamp;
}
