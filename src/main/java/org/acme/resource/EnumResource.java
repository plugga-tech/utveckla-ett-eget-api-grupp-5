package org.acme.resource;

import org.acme.service.EnumService;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;

import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

/**
 * Den här klassen kan användas för att lista alla olika typer av enums
 */

@Path("/api/enum")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class EnumResource {

    @Inject
    EnumService enumService;

    @GET
    @Operation(summary = "Retrieves all races", description = "Retrieves a list of all races defined in the system.")
    @APIResponse(responseCode = "200", description = "Successfully retrieved list of races")
    @APIResponse(responseCode = "500", description = "Server error when retrieving races, check server logs for more information")
    @APIResponse(responseCode = "401", description = "User error when retrieving races, invalid API key or similar")
    @Path("/races")
    public Response getRaces() {
        return Response.ok(enumService.getAllRaces()).build();
    }


    @GET
    @Operation(summary = "Retrieves all weapons", description = "Retrieves a list of all weapons defined in the system.")
    @APIResponse(responseCode = "200", description = "Successfully retrieved list of weapons")
    @APIResponse(responseCode = "500", description = "Server error when retrieving weapons, check server logs for more information")
    @APIResponse(responseCode = "401", description = "User error when retrieving weapons, invalid API key or similar")
    @Path("/weapons")
    public Response getWeapons() {
        return Response.ok(enumService.getAllWeapons()).build();
    }


    @GET
    @Operation(summary = "Retrieves all classes", description = "Retrieves a list of all classes defined in the system.")
    @APIResponse(responseCode = "200", description = "Successfully retrieved list of classes")
    @APIResponse(responseCode = "500", description = "Server error when retrieving classes, check server logs for more information")
    @APIResponse(responseCode = "401", description = "User error when retrieving classes, invalid API key or similar")
    @Path("/classes")
    public Response getClasses() {
        return Response.ok(enumService.getAllClasses()).build();
    }



}
