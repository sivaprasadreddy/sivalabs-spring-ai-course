package com.sivalabs.mcpclient;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.SimpleLoggerAdvisor;
import org.springframework.ai.tool.ToolCallbackProvider;
import org.springframework.web.bind.annotation.*;

@RestController
public class VideoController {
    private static final Logger log = LoggerFactory.getLogger(VideoController.class);
    private final ChatClient chatClient;

    public VideoController(ChatClient.Builder chatClientBuilder,
                           ToolCallbackProvider tools) {
        this.chatClient = chatClientBuilder
                .defaultAdvisors(new SimpleLoggerAdvisor())
                .defaultToolCallbacks(tools)
                .build();
    }

    @PostMapping("/ask")
    public Answer ask(@RequestBody Question question) {
        var response = chatClient.prompt()
                .user(question.question())
                .call()
                .content();
        log.debug("Response from AI: {}", response);
        String markdownAnswer = formatResponse(question, response);
        log.debug("Markdown formatted response: {}", markdownAnswer);
        var htmlResponse = MarkdownHelper.toHTML(markdownAnswer);
        log.debug("HTML formatted response: {}", htmlResponse);
        return new Answer(htmlResponse);
    }

    private String formatResponse(Question question, String answer) {
        var prompt = """
                Following are the question and answer:
                
                Question: {question}
                
                Answer: {answer}
                
                Format the answer into plain human readable text and return only the formatted response.
                """;
        return chatClient
                .prompt()
                .user(u -> u.text(prompt)
                        .param("question", question.question())
                        .param("answer", answer)
                )
                .call()
                .content();
    }

    public record Question(String question) {}

    public record Answer(String answer) {}
}
