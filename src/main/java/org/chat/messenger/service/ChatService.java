package org.chat.messenger.service;

import org.chat.messenger.model.Message;
import org.chat.messenger.model.Room;
import org.chat.messenger.model.repository.MessageRepository;
import org.chat.messenger.model.repository.RoomRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ChatService {
    @Autowired
    private MessageRepository messageRepository;
    @Autowired
    private RoomRepository roomRepository;

    // Saves the message
    public Message saveMessage(Message message) {
        messageRepository.save(message);
        return message;
    }

    public Room saveRoom(Room room) {
        roomRepository.save(room);
        return room;
    }

    public List<Message> findMessagesByRoomId(String roomId) {
        return messageRepository.findByRoomId(roomId);
    }

    public Room findRoomById(String roomId) {
        return roomRepository.findById(roomId);
    }
}