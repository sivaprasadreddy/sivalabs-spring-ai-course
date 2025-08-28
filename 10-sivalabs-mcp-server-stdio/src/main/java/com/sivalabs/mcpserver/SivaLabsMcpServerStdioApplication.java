package com.sivalabs.mcpserver;

import org.springframework.ai.support.ToolCallbacks;
import org.springframework.ai.tool.ToolCallback;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.List;

@SpringBootApplication
public class SivaLabsMcpServerStdioApplication {

	public static void main(String[] args) {
		SpringApplication.run(SivaLabsMcpServerStdioApplication.class, args);
	}

	@Bean
	public List<ToolCallback> toolCallbacks(VideoTools videoTools) {
		return List.of(ToolCallbacks.from(videoTools));
	}

}
