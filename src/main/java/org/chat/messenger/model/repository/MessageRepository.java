package org.chat.messenger.model.repository;

import org.chat.messenger.model.Message;
import org.chat.messenger.model.Status;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MessageRepository extends JpaRepository<Message, Long> {
    List<Message> findByChatId(String chatId);
}
