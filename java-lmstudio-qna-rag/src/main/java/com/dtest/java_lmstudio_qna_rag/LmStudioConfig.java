package com.dtest.java_lmstudio_qna_rag;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.openai.OpenAiChatModel;
import org.springframework.ai.openai.api.OpenAiApi;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.JdkClientHttpRequestFactory;
import org.springframework.http.client.reactive.JdkClientHttpConnector;
import org.springframework.web.client.RestClient;
import org.springframework.web.reactive.function.client.WebClient;


import java.net.http.HttpClient;
import java.time.Duration;

@Configuration
public class LmStudioConfig {

    @Bean
    public ChatClient.Builder chatClientBuilder() {
        OpenAiApi openAiApi = OpenAiApi.builder()
                .baseUrl("http://localhost:1234")
                .apiKey(() -> "no-key-needed") // LM Studio doesn't need a real key
                .webClientBuilder(WebClient.builder()
                        .clientConnector(new JdkClientHttpConnector(HttpClient.newBuilder()
                                .version(HttpClient.Version.HTTP_1_1)
                                .connectTimeout(Duration.ofSeconds(30))
                                .build())))
                .restClientBuilder(RestClient.builder()
                        .requestFactory(new JdkClientHttpRequestFactory(HttpClient.newBuilder()
                                .version(HttpClient.Version.HTTP_1_1)
                                .connectTimeout(Duration.ofSeconds(30))
                                .build())))
                .build();

        OpenAiChatModel chatModel = new OpenAiChatModel(openAiApi);
        return ChatClient.builder(chatModel);
    }
}