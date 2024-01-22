package com.ssafy.msg.test.model.dto;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import com.ssafy.msg.user.model.dto.AccessTokenDto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "test")
public class TestDto {

	  @Id
	  private String id;

	  private String firstName;
	  private String lastName;

}
