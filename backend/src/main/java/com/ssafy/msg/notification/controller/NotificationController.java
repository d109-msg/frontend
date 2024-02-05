package com.ssafy.msg.notification.controller;

import com.ssafy.msg.notification.model.service.NotificationService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/notification")
@RequiredArgsConstructor
@Slf4j

@Tag(name="Notification", description="알림 관련 API")
public class NotificationController {


    private final NotificationService notificationService;

}
