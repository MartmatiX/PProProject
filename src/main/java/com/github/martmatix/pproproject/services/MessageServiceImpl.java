package com.github.martmatix.pproproject.services;

import com.github.martmatix.pproproject.database.entities.MessageEntity;
import com.github.martmatix.pproproject.database.repositories.MessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MessageServiceImpl implements MessageService {

    private MessageRepository messageRepository;

    @Override
    public List<MessageEntity> findMessagesBetweenUsers(String sender, String receiver) {
        return messageRepository.findAllBySenderNameAndReceiverNameOrSenderNameAndReceiverNameOrderByIdDesc(sender, receiver, receiver, sender);
    }

    @Override
    public void saveMessage(MessageEntity message) {
        messageRepository.save(message);
    }

    @Autowired
    public void setMessageRepository(MessageRepository messageRepository) {
        this.messageRepository = messageRepository;
    }
}
