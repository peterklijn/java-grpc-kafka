package com.peterklijn.service.client;

import com.fasterxml.jackson.annotation.JsonCreator;

import io.dropwizard.Configuration;

public class ClientServiceConfiguration extends Configuration {

  public final String kafka;

  @JsonCreator
  public ClientServiceConfiguration(final String kafka) {
    this.kafka = kafka;
  }
}
