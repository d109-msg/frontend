package com.ssafy.msg.scheduler.model.service;

import java.sql.SQLException;


public interface SchedulerService {
    void gameAM8() throws Exception;
    void gamePM6() throws SQLException;
    void gamePM8() throws Exception;
    void gameAM12() throws SQLException;

    int getFlagNight() throws Exception;

}
