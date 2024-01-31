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

    void startRandomGame() throws Exception;
}
