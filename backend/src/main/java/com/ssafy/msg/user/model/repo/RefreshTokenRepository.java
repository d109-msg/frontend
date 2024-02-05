package com.ssafy.msg.user.model.repo;

import com.ssafy.msg.user.model.entity.RefreshTokenEntity;
import org.springframework.data.repository.CrudRepository;

public interface RefreshTokenRepository extends CrudRepository<RefreshTokenEntity, String> {

}

