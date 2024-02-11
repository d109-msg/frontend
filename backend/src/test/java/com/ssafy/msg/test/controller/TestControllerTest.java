package com.ssafy.msg.test.controller;

import com.ssafy.msg.message.model.repo.MessageRepository;
import com.ssafy.msg.message.model.service.MessageService;
import com.ssafy.msg.message.util.DateTimeUtil;
import com.ssafy.msg.notification.model.repo.NotificationRepository;
import com.ulisesbocchio.jasyptspringboot.annotation.EnableEncryptableProperties;
import com.ulisesbocchio.jasyptspringboot.configuration.EnableEncryptablePropertiesConfiguration;
import org.junit.jupiter.api.*;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

@SpringBootTest
@AutoConfigureMockMvc
@TestPropertySource(locations = {"classpath:application.yml", "classpath:application-secret.yml", "classpath:application-test.yml"})
class TestControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @BeforeAll
    static void beforeAll() {
        System.out.println("Before All");
    }

    @AfterAll
    static void afterAll() {
        System.out.println("After All");
    }

    @BeforeEach
    void beforeEach() {
        System.out.println("Before Each");
    }

    @AfterEach
    void afterEach() {
        System.out.println("After Each");
    }

    @Test
    @DisplayName("동기 API 테스트")
    void syncApi() throws Exception {
        long startTime = System.currentTimeMillis();

        for (int i = 0; i < 20; i++) {
            mockMvc.perform(get("/test/sync")); // 동기 API 호출
        }

        long endTime = System.currentTimeMillis();
        long elapsedTime = endTime - startTime;
        System.out.println("Elapsed Time for 20 Sync API calls: " + elapsedTime + " milliseconds");
    }

    @Test
    @DisplayName("비동기 API 테스트")
    void asyncApi() throws Exception {
        long startTime = System.currentTimeMillis();

        List<CompletableFuture<String>> futures = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            CompletableFuture<String> future = CompletableFuture.supplyAsync(() -> {
                try {
                    MvcResult mvcResult = mockMvc.perform(get("/test/async")).andReturn(); // 비동기 API 호출
                    return mvcResult.getResponse().getContentAsString(); // API 호출 결과 반환
                } catch (Exception e) {
                    e.printStackTrace();
                    return null;
                }
            });
            futures.add(future);
        }

        CompletableFuture.allOf(futures.toArray(new CompletableFuture[0])).join(); // 모든 비동기 작업이 완료될 때까지 대기

        long endTime = System.currentTimeMillis();
        long elapsedTime = endTime - startTime;
        System.out.println("Elapsed Time for 20 Async API calls: " + elapsedTime + " milliseconds");
    }

}