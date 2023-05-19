package org.chat.messenger.service;

import org.chat.messenger.model.Message;
import org.chat.messenger.model.MessageStatus;
import org.chat.messenger.model.repository.MessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ChatService {
    @Autowired
    private MessageRepository messageRepository;

    // Saves the message
    public Message save(Message message) {
        message.setStatus(MessageStatus.DELIVERED);
        messageRepository.save(message);
        return message;
    }
}