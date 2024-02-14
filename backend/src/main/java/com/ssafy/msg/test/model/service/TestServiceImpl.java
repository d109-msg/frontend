package com.ssafy.msg.test.model.service;

import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;

@Service
public class TestServiceImpl implements TestService {
    @Override
    public String doSyncTest() throws InterruptedException {
        try {
            Thread.sleep(5000); // 5초 동안 대기
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // 결과 반환
        return "Sync API Response";
    }

    @Override
    public String doSyncTest2() throws InterruptedException {
        try {
            Thread.sleep(10000); // 10초 동안 대기
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // 결과 반환
        return "Sync API Response";
    }


    @Async
    @Override
    public CompletableFuture<String> doAsyncTest() throws InterruptedException {
        CompletableFuture<String> future = CompletableFuture.supplyAsync(() -> {
            try {
                Thread.sleep(5000); // 5초 동안 대기
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            // 결과 반환
            return "Async API Response";
        });

        // CompletableFuture 객체 반환
        return future;
    }

    @Async
    @Override
    public CompletableFuture<String> doAsyncTest2() throws InterruptedException {
        CompletableFuture<String> future = CompletableFuture.supplyAsync(() -> {
            try {
                Thread.sleep(10000); // 10초 동안 대기
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            // 결과 반환
            return "Async API Response";
        });

        // CompletableFuture 객체 반환
        return future;
    }

    @Async
    @Override
    public void doSyncTest3() throws InterruptedException {
        try {
            Thread.sleep(10000); // 10초 동안 대기
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
