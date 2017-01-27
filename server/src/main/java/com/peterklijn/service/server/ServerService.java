package com.peterklijn.service.server;

import com.peterklijn.service.server.producers.KafkaProducerManager;
import com.peterklijn.service.server.resources.ThingResource;

import io.dropwizard.Application;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;

public class ServerService extends Application<ServerServiceConfiguration> {


  @Override
  public void initialize(final Bootstrap<ServerServiceConfiguration> bootstrap) {
    super.initialize(bootstrap);
    bootstrap.setObjectMapper(ObjectMapperFactory.makeMapper());
  }

  public void run(final ServerServiceConfiguration config, final Environment environment) throws Exception {
    final KafkaProducerManager kafkaManager = new KafkaProducerManager(config.kafka);
    environment.lifecycle().manage(kafkaManager);

    environment.jersey().register(new ThingResource(kafkaManager));
  }

  public static void main(final String[] args) throws Exception {
    new ServerService().run(args);
  }

}
