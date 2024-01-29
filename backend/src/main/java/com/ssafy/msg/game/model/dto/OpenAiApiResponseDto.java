package com.ssafy.msg.game.model.dto;

import lombok.Data;

import java.util.List;

@Data
public class OpenAiApiResponseDto {
    private String id;
    private String object;
    private long created;
    private String model;
    private Usage usage;
    private List<Choice> choices;

    @Data
    public static class Usage {
        private int promptTokens;
        private int completionTokens;
        private int totalTokens;
    }

    @Data
    public static class Choice {
        private Message message;
        private String finishReason;
        private int index;

        @Data
        public static class Message {
            private String role;
            private String content;
        }
    }
}