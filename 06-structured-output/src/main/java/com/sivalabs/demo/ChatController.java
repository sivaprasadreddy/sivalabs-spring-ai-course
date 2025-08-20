package com.sivalabs.demo;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.SimpleLoggerAdvisor;
import org.springframework.ai.chat.messages.Message;
import org.springframework.ai.chat.messages.SystemMessage;
import org.springframework.ai.chat.messages.UserMessage;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.chat.prompt.PromptTemplate;
import org.springframework.ai.converter.BeanOutputConverter;
import org.springframework.ai.converter.ListOutputConverter;
import org.springframework.ai.converter.MapOutputConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import static java.nio.charset.StandardCharsets.UTF_8;

@RestController
@RequestMapping("/")
class ChatController {
    private static final Logger log = LoggerFactory.getLogger(ChatController.class);

    private final ChatClient chatClient;
    private final Resource tweetSystemMsgResource;

    ChatController(ChatClient.Builder builder,
                   @Value("classpath:/prompts/tweet-system-message.st")
                   Resource tweetSystemMsgResource) {
        this.chatClient = builder
                .defaultAdvisors(new SimpleLoggerAdvisor())
                .build();
        this.tweetSystemMsgResource = tweetSystemMsgResource;
    }

    @PostMapping("/api/chat")
    Output chat(@RequestBody @Valid Input input) {
        /*
        String response = chatClient.prompt(input.prompt()).call().content();
        return new Output(response);
        */

        //String systemPrompt = "You are a friendly, helpful assistant. You always respond professionally.";
        String systemPrompt = "You are a funny, and helpful assistant. You always respond in a sarcastic manner.";
        SystemMessage systemMessage = new SystemMessage(systemPrompt);
        UserMessage userMessage = new UserMessage(input.prompt());

        Prompt prompt = new Prompt(List.of(systemMessage, userMessage));
        String response = chatClient.prompt(prompt).call().content();

        return new Output(response);
    }

    record Input(@NotBlank String prompt) {}
    record Output(String content) {}

    record TitleSuggestionsRequest(@NotBlank String topic, @NotNull Integer count) {}
    record TitleSuggestionsResponse(List<String> titles) {}

    @PostMapping("/api/suggest-titles")
    TitleSuggestionsResponse suggestTitles(@RequestBody @Valid TitleSuggestionsRequest req) {
        String response;
        ListOutputConverter outputConverter = new ListOutputConverter();

        PromptTemplate pt = new PromptTemplate("""
        I would like to give a presentation about the following:
        
        {topic}
        
        Give me {count} title suggestions for this topic.
        
        Make sure the title is relevant to the topic and it should be a single short sentence.
        
        {format}
        """);

        Map<String, Object> vars = Map.of("topic", req.topic(), "count", req.count(),
                "format", outputConverter.getFormat());
        Message message = pt.createMessage(vars);
        response = chatClient.prompt().messages(message).call().content();

        List<String> titles = outputConverter.convert(response);

        return new TitleSuggestionsResponse(titles);
    }

    @GetMapping("/api/langs")
    Map<String, Object> languages() {
        String response;
        MapOutputConverter outputConverter = new MapOutputConverter();

        PromptTemplate pt = new PromptTemplate("""
        Return all popular programming languages and their inception year.
        
        {format}
        """);

        Map<String, Object> vars = Map.of("format", outputConverter.getFormat());
        Message message = pt.createMessage(vars);
        response = chatClient.prompt().messages(message).call().content();

        Map<String, Object> languages = outputConverter.convert(response);
        log.info("Languages: {}", languages);
        return languages;
    }



    record Tweet(String content, List<String> hashtags) {}

    @PostMapping("/api/gen-tweet")
    Tweet generateTweet(@RequestBody @Valid Input input) throws IOException {
        String systemPrompt = tweetSystemMsgResource.getContentAsString(UTF_8);
        SystemMessage systemMessage = new SystemMessage(systemPrompt);

        PromptTemplate pt = new PromptTemplate("""
        Generate a tweet for the following content:
        
        {content}
        
        {format}
        """);

        BeanOutputConverter<Tweet> beanOutputConverter = new BeanOutputConverter<>(Tweet.class);
        String format = beanOutputConverter.getFormat();
        Map<String, Object> vars = Map.of("content", input.prompt(), "format", format);

        Message userMessage = pt.createMessage(vars);

        Prompt prompt = new Prompt(List.of(systemMessage, userMessage));
        String response = chatClient.prompt(prompt).call().content();

        Tweet tweet = beanOutputConverter.convert(response);
        log.info("Generated tweet: {}", tweet);
        return tweet;
    }
}