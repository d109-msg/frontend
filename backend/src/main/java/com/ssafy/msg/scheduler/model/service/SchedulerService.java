package com.ssafy.msg.scheduler.model.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;


public interface SchedulerService {
    void gameAM8() throws Exception;
    void gamePM6();
    void gamePM8();
    void gameAM12();

}
