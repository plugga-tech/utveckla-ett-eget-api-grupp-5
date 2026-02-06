package org.acme.resource;

import io.quarkus.security.Authenticated;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;



@Authenticated
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class AdminResource {

    @Inject
    EntityManager em;


    @GET
    @Produces(MediaType.TEXT_PLAIN) 
    @Path("/find-user")
    public String adminResource(){
        em.createQuery("SELECT h FROM Hero h WHERE h.name = 'gimli'"); // Bara testar så att behörighet fungerar. 
        return "admin";
    }



}
