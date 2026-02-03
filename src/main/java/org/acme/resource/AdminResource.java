package org.acme.resource;

import jakarta.annotation.security.PermitAll;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

@Path("/api/admin")
public class AdminResource {

    @GET
    @PermitAll
    @Produces(MediaType.TEXT_PLAIN) 
    public String adminResource(){

        return "admin";
    }

}
