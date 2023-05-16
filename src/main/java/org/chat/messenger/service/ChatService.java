package org.chat.messenger.service;

import org.chat.messenger.model.Message;
import org.chat.messenger.model.Status;
import org.chat.messenger.model.repository.MessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ChatService {
    @Autowired
    private MessageRepository messageRepository;

    // Saves the message
    public Message save(Message message) {
        message.setStatus(Status.DELIVERED);
        messageRepository.save(message);
        return message;
    }

    // Finds message by given ID
    public Message findById(String id) {
        Optional<Message> optionalMessage = messageRepository.findById(Long.valueOf(id));
        if (optionalMessage.isPresent()) {
            Message message = optionalMessage.get();
            messageRepository.save(message);
            return message;
        } else {
            return null;
        }
    }
}