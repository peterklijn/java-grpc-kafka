package com.peterklijn.service.server;

import com.fasterxml.jackson.annotation.JsonCreator;

import io.dropwizard.Configuration;

public class ServerServiceConfiguration extends Configuration {

  public final String kafka;

  @JsonCreator
  public ServerServiceConfiguration(final String kafka) {
    this.kafka = kafka;
  }
}
