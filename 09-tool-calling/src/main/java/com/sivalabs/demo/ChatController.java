package com.sivalabs.demo;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.SimpleLoggerAdvisor;
import org.springframework.ai.chat.prompt.PromptTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/")
class ChatController {
    private final ChatClient chatClient;

    ChatController(ChatClient.Builder builder, EmployeeTools employeeTools) {
        this.chatClient = builder
                .defaultSystem("""
                You are a helpful assistant for SivaLabs company.
                You always respond based on the data you have from tools available to you.
                If you don't know the answer, you will respond with "I don't know".
                """)
                .defaultTools(employeeTools, new DateTimeTools())
                .defaultAdvisors(new SimpleLoggerAdvisor())
                .build();
    }

    @PostMapping("/api/chat")
    Output chat(@RequestBody @Valid Input input) {
        String response = chatClient
                .prompt(input.prompt()).call().content();
        return new Output(response);
    }

    record Input(@NotBlank String prompt) {}
    record Output(String content) {}

}