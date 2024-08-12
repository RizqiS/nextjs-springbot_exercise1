package com.subrutin.catalog.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.subrutin.catalog.model.MovieStatus;
import com.subrutin.catalog.util.MovieStatusDeserializer;

@Configuration
public class JacksonConfig {

  @Bean
  public ObjectMapper objectMapper() {
    var objectMapper = new ObjectMapper();
    var simpleModule = new SimpleModule();

    simpleModule.addDeserializer(MovieStatus.class, new MovieStatusDeserializer());
    objectMapper.registerModule(simpleModule);
    return objectMapper;
  }

}
