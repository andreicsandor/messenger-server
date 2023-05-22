package org.chat.messenger.controller;

import org.chat.messenger.dto.MessageDTO;
import org.chat.messenger.dto.NotificationDTO;
import org.chat.messenger.model.*;
import org.chat.messenger.service.AccountService;
import org.chat.messenger.service.ChatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@RestController
public class ChatController {

    @Autowired
    private AccountService accountService;
    @Autowired
    private ChatService chatService;
    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    @MessageMapping("/notifications")
    public void processNotification(NotificationDTO notificationDTO) {
        NotificationType type = notificationDTO.getType();
        String sender = notificationDTO.getSender();
        String recipient = notificationDTO.getRecipient();
        String content = notificationDTO.getContent();

        Notification notification = new Notification();
        notification.setType(type);
        notification.setSender(sender);
        notification.setRecipient(recipient);
        notification.setContent(content);

        // Send notification to client
        if (type == NotificationType.MESSAGE || type == NotificationType.PING) {
            messagingTemplate.convertAndSendToUser(notification.getRecipient(), "/notifications", notification);
        } else if (type == NotificationType.ONLINE || type == NotificationType.OFFLINE) {
            messagingTemplate.convertAndSend("/public/notifications", notification);
        }

    }

    @MessageMapping("/chat")
    public void processMessage(MessageDTO messageDTO) {
        String senderUsername = messageDTO.getSender();
        String recipientUsername = messageDTO.getRecipient();
        String content = messageDTO.getContent();

        String roomId = setRoomId(senderUsername, recipientUsername);
        Room room = chatService.findRoomById(roomId);

        Account sender = accountService.findAccountByUsername(senderUsername);
        Account recipient = accountService.findAccountByUsername(recipientUsername);

        // If room does not exist, create it
        if (room == null) {
            room = new Room(roomId, sender, recipient, RoomStatus.READ, RoomStatus.UNREAD);
            room = chatService.saveRoom(room);
        } else {
            if (room.getUserOne().getUsername().equals(senderUsername)) {
                room.setUserOneStatus(RoomStatus.READ);
                room.setUserTwoStatus(RoomStatus.UNREAD);
            } else {
                room.setUserOneStatus(RoomStatus.UNREAD);
                room.setUserTwoStatus(RoomStatus.READ);
            }
            room = chatService.saveRoom(room);
        }

        Message message = new Message();
        message.setSender(senderUsername);
        message.setRecipient(recipientUsername);
        message.setContent(content);
        message.setRoom(room);

        // Send message to client
        Message savedMessage = chatService.saveMessage(message);
        messagingTemplate.convertAndSendToUser(message.getRecipient(),"/messages", savedMessage);
    }

    @GetMapping("/api/messages/conversation/{roomId}")
    public ResponseEntity<?> findMessagesByRoomId(@PathVariable String roomId) {
        return ResponseEntity.ok(chatService.findMessagesByRoomId(roomId));
    }

    @GetMapping("/api/contacts")
    public ResponseEntity<?> findContacts () {
        return ResponseEntity.ok(accountService.listAccounts());
    }

    @GetMapping("/api/active-contacts")
    public ResponseEntity<?> findActiveContacts () {
        return ResponseEntity.ok(accountService.listActiveUsers());
    }

    private String setRoomId(String sender, String recipient) {
        List<String> ids = Arrays.asList(sender, recipient);
        Collections.sort(ids);
        return String.join("_", ids);
    }
}
