package com.sivalabs.demo;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.assertj.MockMvcTester;
import org.springframework.test.web.servlet.assertj.MvcTestResult;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@AutoConfigureMockMvc
class ApplicationTests {
    @Autowired
    MockMvcTester mockMvcTester;

    @Test
    void chat() {
        MvcTestResult testResult = mockMvcTester.post().uri("/api/chat")
                .contentType(MediaType.APPLICATION_JSON)
                .content("""
                        {
                            "prompt": "Why Java is so popular in enterprise software development?"
                        }
                        """)
                .exchange();
        assertThat(testResult)
                .hasStatus(HttpStatus.OK)
                .bodyJson()
                .convertTo(ChatController.Output.class).satisfies(output -> {
                    assertThat(output.content()).isNotBlank();
                    System.out.println("Response:\n"+output.content());
                });
    }

    @Test
    void suggestTitles() {
        MvcTestResult testResult = mockMvcTester.post().uri("/api/suggest-titles")
                .contentType(MediaType.APPLICATION_JSON)
                .content("""
                        {
                            "topic": "Spring Boot Tips and Tricks",
                            "count": 3
                        }
                        """)
                .exchange();
        assertThat(testResult)
                .hasStatus(HttpStatus.OK)
                .bodyJson()
                .convertTo(ChatController.Output.class).satisfies(output -> {
                    assertThat(output.content()).isNotBlank();
                    System.out.println("Response:\n"+output.content());
                });
    }

    @Test
    void generateTweet() {
        MvcTestResult testResult = mockMvcTester.post().uri("/api/gen-tweet")
                .contentType(MediaType.APPLICATION_JSON)
                .content("""
                        {
                            "prompt": "IntelliJ IDEA 2025.2 is released with a lot of new features such as Java 25 Early Access, Maven 4, JSpecify for nullability checks, Spring Debugger plugin, Spring Modulith support. Checkout https://blog.jetbrains.com/idea/2025/08/intellij-idea-2025-2/ for more details."
                        }
                        """)
                .exchange();
        assertThat(testResult)
                .hasStatus(HttpStatus.OK)
                .bodyJson()
                .convertTo(ChatController.Output.class).satisfies(output -> {
                    assertThat(output.content()).isNotBlank();
                    System.out.println("Response:\n"+output.content());
                });
    }

}
