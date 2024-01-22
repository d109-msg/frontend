package com.ssafy.msg.game.model.mapper;

import com.ssafy.msg.chat.model.dto.RoomDto;
import com.ssafy.msg.game.model.dto.EnterGroupRoomDto;
import com.ssafy.msg.game.model.dto.ParticipantDto;
import com.ssafy.msg.game.model.dto.RandomNameDto;
import com.ssafy.msg.game.model.dto.VoteResultDto;
import com.ssafy.msg.user.model.dto.UserDto;
import org.apache.ibatis.annotations.Mapper;

import java.sql.SQLException;
import java.util.List;

@Mapper
public interface GameMapper {
    void applyRandomGame(UserDto userDto) throws SQLException;

    boolean isParticipantInRoom(EnterGroupRoomDto enterGroupRoomDto) throws SQLException;

    List<RandomNameDto> getRandomNicknames(int limit) throws SQLException;

    RandomNameDto getRandomRoomName() throws SQLException;

    int insertParticipants(List<ParticipantDto> participants) throws SQLException;

    List<RoomDto> getUserRooms(String userEmail) throws SQLException;

    List<VoteResultDto> getRoomVote(String roomId) throws SQLException;
}
