package com.ssafy.msg.test.model.service;

import java.util.concurrent.CompletableFuture;

public interface TestService {
    public String doSyncTest() throws InterruptedException;
    public CompletableFuture<String> doAsyncTest() throws InterruptedException;

    public String doSyncTest2() throws InterruptedException;

    public CompletableFuture<String> doAsyncTest2() throws InterruptedException;

    public void doSyncTest3() throws InterruptedException;
}
