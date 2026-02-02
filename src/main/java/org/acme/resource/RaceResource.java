package org.acme.resource;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.core.Response;

@Path("/api/race")
public class RaceResource {

    @GET
    public Response getRaces() {
        return Response.ok().build();
    }


    @POST
    public Response PostRace(){
        return Response.ok().build();
    }

}
