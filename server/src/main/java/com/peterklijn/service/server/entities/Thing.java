package com.peterklijn.service.server.entities;

import com.fasterxml.jackson.annotation.JsonCreator;

public class Thing {
  public final String name;

  @JsonCreator
  public Thing(final String name) {
    this.name = name;
  }

  @Override
  public String toString() {
    return "Thing{" +
        "name='" + name + '\'' +
        '}';
  }
}
