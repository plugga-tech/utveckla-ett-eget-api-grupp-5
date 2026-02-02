package org.acme.resource;

import java.util.List;

import org.acme.model.Race;
import org.acme.service.RaceService;

import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/api/race")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class RaceResource {

    @Inject
    RaceService raceService;

    @GET
    @Path("/all")
    public Response getRaces() {

        List<Race> races = raceService.findAll();
        
        if (races.isEmpty()){
            return Response.noContent().build();
        }

        return Response.ok(races).build();
    }


    @POST
    public Response PostRace(){
        return Response.ok().build();
    }

}
