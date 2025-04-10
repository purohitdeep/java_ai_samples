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
        String response = ollamaClient.prompt()
                .user(message)
                .call().content();
        String color = "\u001B[34m";
        String reset = "\u001B[0m";
        System.out.println(color + response + reset);
        return response;
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
        // Initialize story with topic
        StringBuilder storyBuilder = new StringBuilder("Topic: ").append(topic).append("\n\n");
        StringBuilder currentStory = new StringBuilder(); // More efficient for concatenation
        boolean isLmStudioTurn = true;

        System.out.println("######## " + topic + " ########");

        // Pre-process the topic substitution in prompts once
        String agentLPrompt = agentLStoryPrompt.replace("[TOPIC]", topic);
        String agentOPrompt = agentOStoryPrompt.replace("[TOPIC]", topic);

        for (int i = 0; i < 20; i++) {
            // Determine which agent to use
            ChatClient speaker = isLmStudioTurn ? lmStudioClient : ollamaClient;
            String prompt = isLmStudioTurn ? agentLPrompt : agentOPrompt;

            // Create user prompt with current story state
            String userPrompt = "Story so far: \"" + currentStory + "\"\nWrite one sentence to continue the story.";

            // Get response from the agent
            String sentence = speaker.prompt()
                    .system(prompt)
                    .user(userPrompt)
                    .call()
                    .content();

            // Format and append the new sentence
            storyBuilder.append(sentence);

            // Add formatting based on content or position in story
            boolean needsParagraphBreak = false;

            // Check for paragraph break indicators
            if (sentence.contains("??")) {
                needsParagraphBreak = true;
            } else if (i > 0 && i % 4 == 0) {
                needsParagraphBreak = true;
            }

            // Add paragraph break if needed
            if (needsParagraphBreak) {
                storyBuilder.append("\n\n");
                currentStory.append(sentence).append("\n\n");
            } else {
                storyBuilder.append(" ");
                currentStory.append(sentence).append(" ");
            }

            // Console output with color coding
            String color = isLmStudioTurn ? "\u001B[34m" : "\u001B[32m"; // Blue or Green
            System.out.print(color + sentence + (needsParagraphBreak ? "\n\n" : " ") + "\u001B[0m");

            // Switch turns
            isLmStudioTurn = !isLmStudioTurn;
        }

        return storyBuilder.toString();
    }

}
