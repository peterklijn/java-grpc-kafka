package com.peterklijn.service.client;

import com.peterklijn.service.client.consumers.ClientKafkaConsumer;
import com.peterklijn.service.client.consumers.KafkaConsumerManager;

import io.dropwizard.Application;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;

public class ClientService extends Application<ClientServiceConfiguration>  {

  @Override
  public void initialize(final Bootstrap<ClientServiceConfiguration> bootstrap) {
    super.initialize(bootstrap);
    bootstrap.setObjectMapper(ObjectMapperFactory.makeMapper());
  }

  public void run(final ClientServiceConfiguration config, final Environment environment) throws Exception {
    System.out.println("run!");
    final ClientKafkaConsumer consumer = new ClientKafkaConsumer(config.kafka, "client-service", "things");
    final KafkaConsumerManager kafkaManager = new KafkaConsumerManager(consumer);

    environment.lifecycle().manage(kafkaManager);
  }

  public static void main(final String[] args) throws Exception {
    new ClientService().run(args);
  }
}
