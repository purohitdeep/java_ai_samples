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

    @Value("${agent.l.story.prompt}")
    private String agentLStoryPrompt;
    @Value("${agent.o.story.prompt}")
    private String agentOStoryPrompt;

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
        System.out.println("######## " + topic + " ########");
        for (int i = 0; i <= 20; i++) {
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

            if ((i % 2 == 1)) {
                transcript.append("\n");
                System.out.println();
            }
        }

        return transcript.toString();
    }

    @GetMapping("/sentence-by-sentence-story")
    public String createSentenceBySentenceStory(@RequestParam(defaultValue = "a magical adventure") String topic) {
        StringBuilder storyBuilder = new StringBuilder("Topic: " + topic + "\n\n");
        boolean isLmStudioTurn = true;

        System.out.println("######## " + topic + " ########");

        String currentStory = ""; // what we pass in every turn

        for (int i = 0; i < 20; i++) {
            ChatClient speaker = isLmStudioTurn ? lmStudioClient : ollamaClient;
            String prompt = isLmStudioTurn ? agentLStoryPrompt : agentOStoryPrompt;
            prompt = prompt.replace("[TOPIC]", topic);

            // System prompt stays consistent, but user prompt evolves with story
            String userPrompt = "Story so far: \"" + currentStory.trim() + "\"\nWrite one sentence to continue the story.";

            String sentence = speaker.prompt()
                    .system(prompt)
                    .user(userPrompt)
                    .call()
                    .content();
                    //.trim();

            storyBuilder.append(sentence).append(" ");
            sentence += " ";

            if (sentence.contains("??")) {
                storyBuilder.append("\n\n");
                sentence += "\n\n";
            }
            if (i > 0 && i % 4 == 0) {
            storyBuilder.append("\n\n");
            sentence += "\n\n";
            }

            currentStory += " " + sentence + " ";

            String speakerName = isLmStudioTurn ? agentLName : agentOName;
            String color = isLmStudioTurn ? "\u001B[34m" : "\u001B[32m"; // Blue or Green
            String reset = "\u001B[0m";

            System.out.print(color + " " +  sentence + reset);

            isLmStudioTurn = !isLmStudioTurn;
        }

        return storyBuilder.toString();
    }
}
