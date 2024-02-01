package com.ssafy.msg.test.model.repo;

import com.ssafy.msg.test.model.dto.TestMongo;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TestMongoRepository extends MongoRepository<TestMongo, String> {

	TestMongo findByFirstName(String firstName);

	List<TestMongo> findByLastName(String lastName);
}
