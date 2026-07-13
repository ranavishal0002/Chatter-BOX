package com.Chatting.Application.service;

import com.Chatting.Application.dto.MessagesDto;

public interface MessagesService {
    MessagesDto saveMessage(MessagesDto messagesDto);
}
