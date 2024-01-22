package com.ssafy.msg.test.model.repo;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.ssafy.msg.test.model.dto.TestDto;

@Repository
public interface TestRepository extends MongoRepository<TestDto, String> {

	TestDto findByFirstName(String firstName);

	List<TestDto> findByLastName(String lastName);
}
