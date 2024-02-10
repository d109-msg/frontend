package com.ssafy.msg.chat.model.mapper;

import com.ssafy.msg.chat.model.dto.CreateRoomDto;
import com.ssafy.msg.chat.model.dto.RoomDto;
import com.ssafy.msg.chat.model.dto.RoomResponseDto;
import com.ssafy.msg.game.model.dto.ParticipantDto;
import org.apache.ibatis.annotations.Mapper;

import java.sql.SQLException;
import java.util.List;

@Mapper
public interface ChatMapper {

	// 상대방과의 채팅방 존재 유무 확인
	RoomDto findRoom(CreateRoomDto createRoomDto) throws SQLException;

	// 존재하지 않을 경우 방 생성
	void createRoom(RoomDto roomDto) throws SQLException;

	// 방 생성 후 사용자 추가
	void enterRoom(ParticipantDto participantDto) throws SQLException;

	// 채팅방 조회
	RoomDto getRoom(String roomId) throws SQLException;

	// 사용자별 일대일 채팅방 목록 조회
	List<RoomResponseDto> getPersonalRoomsInfoById(int id) throws SQLException;


}
