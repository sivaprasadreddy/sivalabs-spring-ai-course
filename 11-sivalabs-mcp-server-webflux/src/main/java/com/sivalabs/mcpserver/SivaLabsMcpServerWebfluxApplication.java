package com.sivalabs.mcpserver;

import org.springframework.ai.support.ToolCallbacks;
import org.springframework.ai.tool.ToolCallback;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.List;

@SpringBootApplication
public class SivaLabsMcpServerWebfluxApplication {

	public static void main(String[] args) {
		SpringApplication.run(SivaLabsMcpServerWebfluxApplication.class, args);
	}

	@Bean
	public List<ToolCallback> toolCallbacks(VideoTools courseService) {
		return List.of(ToolCallbacks.from(courseService));
	}
}
