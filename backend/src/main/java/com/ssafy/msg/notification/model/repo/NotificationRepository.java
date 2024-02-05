package com.ssafy.msg.notification.model.repo;

import com.ssafy.msg.notification.model.entity.NotificationEntity;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NotificationRepository extends MongoRepository<NotificationEntity, String> {

    List<NotificationEntity> findByUserId(int userId);
}
