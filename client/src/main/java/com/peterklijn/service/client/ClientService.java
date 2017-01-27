package com.peterklijn.service.client;

import io.dropwizard.Application;
import io.dropwizard.configuration.EnvironmentVariableSubstitutor;
import io.dropwizard.configuration.SubstitutingSourceProvider;
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
  }

  public static void main(final String[] args) throws Exception {
    new ClientService().run(args);
  }
}
