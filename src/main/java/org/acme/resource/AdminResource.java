package org.acme.resource;

import io.quarkus.security.User;
import jakarta.annotation.security.RolesAllowed;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;


@RolesAllowed("admin")
@Path("/api/admin")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class AdminResource {

    @Inject
    EntityManager em;

    @GET
    @Produces(MediaType.TEXT_PLAIN) 
    @Path("/find-user")
    public String adminResource(){
        em.createQuery("SELECT h FROM Hero h WHERE h.name = 'gimli'");
        System.err.println("asd");
        return "admin";
    }



}
