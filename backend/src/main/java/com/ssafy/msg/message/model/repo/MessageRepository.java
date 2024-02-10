package com.ssafy.msg.message.model.repo;

import com.ssafy.msg.message.model.entity.MessageEntity;
import org.bson.types.ObjectId;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.Aggregation;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;
import java.util.Optional;

public interface MessageRepository extends MongoRepository<MessageEntity, String> {

    List<MessageEntity> findByRoomId(String roomId);


    @Query(value = "{'roomId': ?0}", sort = "{'_id': -1}")
    List<MessageEntity> findMessagesByRoomIdWithLimit(String roomId, Pageable pageable);

    @Aggregation(pipeline = {
            "{ $match : { 'roomId' : ?0, '_id' : { '$lt': ?1 } } }",
            "{ $sort : { '_id' : -1 } }",
            "{ $limit : ?2 }",
            })
    List<MessageEntity> findMessagesByRoomIdAndIdLessThan(String roomId, ObjectId id, int limit);

    @Aggregation(pipeline = {
            "{ $match : { 'roomId' : ?0 } }",
            "{ $sort : { '_id' : -1 } }",
            "{ $limit : ?1 }",
            })
    List<MessageEntity> findMessagesByRoomIdOrderByDescending(String roomId, int limit);

    @Aggregation(pipeline = {
            "{ $match : { 'roomId' : ?0 } }",
            "{ $sort : { '_id' : -1 } }",
            "{ $limit : 1 }",
    })
    MessageEntity findLastMessageByRoomId(String roomId);
}
