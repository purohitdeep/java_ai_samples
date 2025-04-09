package com.dtest.lm_studio_ollama_agents;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.image.ImageModel;
import org.springframework.ai.ollama.OllamaChatModel;
import org.springframework.ai.openai.audio.speech.SpeechModel;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AiController {
    private final ChatClient lmStudioClient;
    private final ChatClient ollamaClient;

    @Value("${agent.l.name}")
    private String agentLName;

    @Value("${agent.o.name}")
    private String agentOName;

    @Value("${agent.l.prompt}")
    private String agentLPrompt;

    @Value("${agent.o.prompt}")
    private String agentOPrompt;

    public AiController(ChatClient.Builder lmStudioChatClientBuilder,
                        OllamaChatModel ollamaChatModel) {
        this.lmStudioClient = lmStudioChatClientBuilder.build();
        this.ollamaClient = ChatClient.builder(ollamaChatModel).build();
    }

    @GetMapping
    public String getAiResponse(@RequestParam(defaultValue = "What is the meaning of life?") String message) {
        return ollamaClient.prompt()
                .user(message)
                .call().content();
    }

    @GetMapping("/conversation")
    public String startConversation(@RequestParam(defaultValue = "the future of AI") String topic) {
        StringBuilder transcript = new StringBuilder("Topic: " + topic + "\n\n");
        String userMessage = "Let's talk about " + topic + ". What do you think?";
        boolean isLmStudioTurn = true;

        for (int i = 0; i < 20; i++) {
            ChatClient speaker = isLmStudioTurn ? lmStudioClient : ollamaClient;
            String speakerName = isLmStudioTurn ? agentLName : agentOName;

            String response = speaker.prompt()
                    .system(isLmStudioTurn ? agentLPrompt : agentOPrompt)
                    .user(userMessage)
                    .call()
                    .content();

            transcript.append(speakerName).append(": ").append(response).append("\n\n");
            // Print to console with color
            String color = isLmStudioTurn ? "\u001B[34m" : "\u001B[32m"; // Blue for LM, Green for Ollama
            String reset = "\u001B[0m";
            System.out.println(color + speakerName + ": " + response + reset);
            userMessage = response;
            isLmStudioTurn = !isLmStudioTurn;
        }

        return transcript.toString();
    }
}
