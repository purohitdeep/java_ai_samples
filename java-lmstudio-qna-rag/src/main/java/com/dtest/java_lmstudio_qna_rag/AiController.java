package com.dtest.java_lmstudio_qna_rag;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.image.ImageModel;

import org.springframework.ai.openai.audio.speech.SpeechModel;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AiController {
    ChatClient chatClient;
    SpeechModel speechModel;
    ImageModel  imageModel;


    public AiController(ChatClient.Builder builder, SpeechModel speechModel, ImageModel imageModel) {
        this.chatClient = builder.build();
        this.speechModel = speechModel;
        this.imageModel = imageModel;
    }

    @GetMapping
    public String getAiResponse(@RequestParam(defaultValue= "What is the meaning of life?") String message) {
       return chatClient.prompt()
               .user(message)
               .call().content();
    }

}
