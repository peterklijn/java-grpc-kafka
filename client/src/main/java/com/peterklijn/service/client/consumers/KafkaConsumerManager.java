package com.peterklijn.service.client.consumers;

import io.dropwizard.lifecycle.Managed;

public class KafkaConsumerManager implements Managed {
  private final ClientKafkaConsumer consumer;
  private Thread consumerTread;

  public KafkaConsumerManager(final ClientKafkaConsumer consumer) {
    this.consumer = consumer;
  }

  @Override
  public void start() throws Exception {
    consumerTread = new Thread(consumer);
    consumerTread.start();
  }

  @Override
  public void stop() throws Exception {
    consumerTread.interrupt();
  }
}
