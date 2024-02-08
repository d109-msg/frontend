package com.ssafy.msg.message.model.repo;

import com.ssafy.msg.message.model.entity.MessageEntity;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;
import java.util.Optional;

public interface MessageRepository extends MongoRepository<MessageEntity, String> {

    List<MessageEntity> findByRoomId(String roomId);


    @Query(value = "{'roomId': ?0}", sort = "{'_id': -1}")
    List<MessageEntity> findMessagesByRoomIdWithLimit(String roomId, Pageable pageable);
}
