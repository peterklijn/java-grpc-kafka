package com.peterklijn.service.server.resources;

import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.container.AsyncResponse;
import javax.ws.rs.container.Suspended;
import javax.ws.rs.core.MediaType;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.peterklijn.service.server.ObjectMapperFactory;
import com.peterklijn.service.server.entities.Thing;
import com.peterklijn.service.server.producers.KafkaProducerManager;

@Path("/things")
@Produces(MediaType.APPLICATION_JSON)
public class ThingResource {
  public final KafkaProducerManager kafkaManager;
  private final ObjectMapper MAPPER = ObjectMapperFactory.makeMapper();

  public ThingResource(final KafkaProducerManager kafkaManager) {
    this.kafkaManager = kafkaManager;
  }

  @POST
  @Path("/")
  public void createThing(final Thing thing, @Suspended final AsyncResponse response) throws JsonProcessingException {
    System.out.println(thing);

    final String json = MAPPER.writeValueAsString(thing);
    kafkaManager
        .send("things", thing.name, json)
        .whenComplete((result, exception) -> {
          if (exception != null) {
            response.resume(exception);
          } else {
            response.resume(thing);
          }
        });
  }

}
