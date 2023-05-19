package org.chat.messenger.controller;

import org.chat.messenger.dto.MessageDTO;
import org.chat.messenger.dto.NotificationDTO;
import org.chat.messenger.model.Message;
import org.chat.messenger.model.Notification;
import org.chat.messenger.model.NotificationType;
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
        if (type == NotificationType.MESSAGE) {
            messagingTemplate.convertAndSendToUser(notification.getRecipient(), "/notifications", notification);
        } else {
            messagingTemplate.convertAndSend("/public/notifications", notification);
        }
    }

    @MessageMapping("/chat")
    public void processMessage(MessageDTO messageDTO) {
        String sender = messageDTO.getSender();
        String recipient = messageDTO.getRecipient();
        String content = messageDTO.getContent();

        String chatId = setChatId(sender, recipient);

        Message message = new Message();
        message.setChatId(chatId);
        message.setSender(sender);
        message.setRecipient(recipient);
        message.setContent(content);

        // Send message to client
        Message savedMessage = chatService.save(message);
        messagingTemplate.convertAndSendToUser(message.getRecipient(),"/messages", savedMessage);

        // Send notification to client
        // TO DO
    }

    @GetMapping("/api/messages/{id}")
    public ResponseEntity<?> findMessage (@PathVariable String id) {
        return ResponseEntity.ok(chatService.findById(id));
    }

    @GetMapping("/api/contacts")
    public ResponseEntity<?> findContacts () {
        return ResponseEntity.ok(accountService.listAccounts());
    }

    @GetMapping("/api/active-contacts")
    public ResponseEntity<?> findActiveContacts () {
        return ResponseEntity.ok(accountService.listActiveUsers());
    }

    private String setChatId(String sender, String recipient) {
        List<String> ids = Arrays.asList(sender, recipient);
        Collections.sort(ids);
        return String.join("_", ids);
    }
}
