package com.ssafy.msg.scheduler.model.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.sql.SQLException;


public interface SchedulerService {
    void gameAM8() throws Exception;
    void gamePM6() throws SQLException;
    void gamePM8() throws Exception;
    void gameAM12() throws SQLException;



    // 미션 미이행자 flag_die 수정
    void manageNonCompleter(String roomId) throws SQLException;
}
