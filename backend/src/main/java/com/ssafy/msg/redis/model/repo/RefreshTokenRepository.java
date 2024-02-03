package com.ssafy.msg.redis.model.repo;

import com.ssafy.msg.redis.model.entity.RefreshTokenEntity;
import org.springframework.data.repository.CrudRepository;

public interface RefreshTokenRepository extends CrudRepository<RefreshTokenEntity, String> {

}

