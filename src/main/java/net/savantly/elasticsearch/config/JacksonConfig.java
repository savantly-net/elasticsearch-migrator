package net.savantly.elasticsearch.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.fasterxml.jackson.databind.ObjectMapper;

@Configuration
public class JacksonConfig {
    
    @Bean
    public ObjectMapper objectMapper() {
        var mapper = new ObjectMapper();
        mapper.findAndRegisterModules();
        mapper.configure(com.fasterxml.jackson.databind.SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
        mapper.configure(com.fasterxml.jackson.databind.SerializationFeature.WRITE_DATE_TIMESTAMPS_AS_NANOSECONDS, false);
        mapper.configure(com.fasterxml.jackson.databind.SerializationFeature.WRITE_DURATIONS_AS_TIMESTAMPS, false);
        mapper.configure(com.fasterxml.jackson.databind.SerializationFeature.WRITE_DATE_TIMESTAMPS_AS_NANOSECONDS, false);
        mapper.configure(com.fasterxml.jackson.databind.SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
        return mapper;
    }

}
