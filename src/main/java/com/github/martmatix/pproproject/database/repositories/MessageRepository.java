package com.github.martmatix.pproproject.database.repositories;

import com.github.martmatix.pproproject.database.entities.MessageEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MessageRepository extends JpaRepository<MessageEntity, Long> {

    List<MessageEntity> findAllBySenderNameAndReceiverName(String senderName, String receiverName);

}
