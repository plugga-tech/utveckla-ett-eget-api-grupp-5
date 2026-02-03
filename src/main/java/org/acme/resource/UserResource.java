package org.acme.resource;

import jakarta.annotation.security.PermitAll;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.SecurityContext;

@Path("/api/users")
public class UserResource {

    @GET
    @PermitAll
    @Produces(MediaType.TEXT_PLAIN)
    @Path("/me")
    public String userResource(@Context SecurityContext securityContext){
        return securityContext.getUserPrincipal().getName();
    }


}
