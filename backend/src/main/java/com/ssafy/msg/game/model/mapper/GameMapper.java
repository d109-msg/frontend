package com.ssafy.msg.game.model.mapper;

import org.apache.ibatis.annotations.Mapper;

import java.sql.SQLException;

@Mapper
public interface GameMapper {
    String[] getRandomNicknames(int limit) throws SQLException;
}
