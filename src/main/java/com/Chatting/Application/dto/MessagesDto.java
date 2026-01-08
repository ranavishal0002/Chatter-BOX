package com.Chatting.Application.dto;


import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Data
@Getter
@Setter
public class MessagesDto {

    private Long id;
    private String sender;
    private String content;
    private LocalDateTime timeStamp;
}
