package com.peterklijn.service.client;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.guava.GuavaModule;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;
import com.fasterxml.jackson.datatype.joda.JodaModule;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.module.afterburner.AfterburnerModule;
import com.fasterxml.jackson.module.paramnames.ParameterNamesModule;

import io.dropwizard.jackson.DiscoverableSubtypeResolver;
import io.dropwizard.jackson.FuzzyEnumModule;
import io.dropwizard.jackson.GuavaExtrasModule;
import io.dropwizard.jackson.LogbackModule;

public class ObjectMapperFactory {

  public static ObjectMapper makeMapper() {
    final ObjectMapper mapper = new ObjectMapper();
    mapper.registerModule(new GuavaModule());
    mapper.registerModule(new LogbackModule());
    mapper.registerModule(new GuavaExtrasModule());
    mapper.registerModule(new JodaModule());
    mapper.registerModule(new AfterburnerModule());
    mapper.registerModule(new FuzzyEnumModule());
    mapper.setSubtypeResolver(new DiscoverableSubtypeResolver());

    mapper.registerModule(new ParameterNamesModule());
    mapper.registerModule(new Jdk8Module());

    mapper.registerModule(new JavaTimeModule());
    mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

    mapper.setPropertyNamingStrategy(PropertyNamingStrategy.SNAKE_CASE);

    return mapper;
  }
}
