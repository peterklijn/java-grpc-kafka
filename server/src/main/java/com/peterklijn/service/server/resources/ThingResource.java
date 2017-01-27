package com.peterklijn.service.server.resources;

import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.container.AsyncResponse;
import javax.ws.rs.container.Suspended;
import javax.ws.rs.core.MediaType;

import com.peterklijn.service.server.entities.Thing;

@Path("/things")
@Produces(MediaType.APPLICATION_JSON)
public class ThingResource {
  public ThingResource() {
  }

  @POST
  @Path("/")
  public void createThing(final Thing thing, @Suspended final AsyncResponse response) {
    System.out.println(thing);
    response.resume(thing);
  }

}
