package com.github.martmatix.pproproject.services;

import com.github.martmatix.pproproject.database.entities.MessageEntity;

import java.util.List;

public interface MessageService {

    List<MessageEntity> findMessagesBetweenUsers(String sender, String receiver);

    void saveMessage(MessageEntity message);

}
