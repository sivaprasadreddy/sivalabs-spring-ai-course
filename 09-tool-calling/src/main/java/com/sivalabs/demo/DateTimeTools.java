package com.sivalabs.demo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ai.tool.annotation.Tool;

import java.time.LocalDate;

class DateTimeTools {
    private static final Logger log = LoggerFactory.getLogger(DateTimeTools.class);

    @Tool(description = "Get the current date in the ISO-8601 format yyyy-MM-dd")
    String getCurrentDate() {
        log.info("Getting current date");
        return LocalDate.now().toString();
    }

}