package com.ssafy.msg.test.model.repo;

import com.ssafy.msg.test.model.dto.TestRedis;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TestRedisRepository extends CrudRepository<TestRedis, Long> {

}
