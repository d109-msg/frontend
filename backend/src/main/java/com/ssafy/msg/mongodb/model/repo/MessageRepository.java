package com.ssafy.msg.mongodb.model.repo;

import com.ssafy.msg.mongodb.model.entity.MessageEntity;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface MessageRepository extends MongoRepository<MessageEntity, String> {

    List<MessageEntity> findByRoomId(String roomId);
}
