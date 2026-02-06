package org.acme.resource;

import org.acme.service.RaceService;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;

import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
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
    @Operation(summary = "Retrieves all races", description = "Retrieves a list of all races defined in the system.")
    @APIResponse(responseCode = "200", description = "Successfully retrieved list of races")
    @APIResponse(responseCode = "500", description = "Server error when retrieving races, check server logs for more information")
    @APIResponse(responseCode = "401", description = "User error when retrieving races, invalid API key or similar")

    @Path("/all")
    public Response getRaces() {
        return Response.ok(raceService.getAllRaces()).build();
    }

}
