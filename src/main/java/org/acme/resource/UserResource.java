package org.acme.resource;


import org.acme.model.UserDto;
import org.acme.service.UserService;
import org.bouncycastle.openssl.PasswordException;

import jakarta.annotation.security.PermitAll;
import jakarta.annotation.security.RolesAllowed;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.SecurityContext;

@Path("/api/users")
public class UserResource {

    @Inject
    UserService userService;

    @Inject
    SecurityContext securityContext;


    @GET
    @Produces(MediaType.TEXT_PLAIN)
    @RolesAllowed("user")
    @Path("/me")
    public Response showApiKey(@Context SecurityContext securityContext){ // Används för att accessa den aktuellt inloggade principalen

        String apiKey = userService.fetchApiKey(securityContext.getUserPrincipal().getName());

        return Response.ok(apiKey).build();

    }



    /*
    förväntar sig anrop i form av JSON
    Typ: 
    {
        "username": "namhär",
        "password": "passwordhär"
    }

    PW måste 
    */
    @POST
    @Transactional
    @Produces(MediaType.TEXT_PLAIN)
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("/new-user")
    public Response newUser (UserDto userDto) {

        try {
            userService.createUser(userDto);
            return Response.ok("User was successfully created. You can now log in to see your API key.").build();

        } catch (PasswordException e) {
            return Response.status(400).entity(e.getMessage()).build(); //returnerar felmeddelande till registreringen
        } catch (IllegalArgumentException e) {
            return Response.status(400).entity(e.getMessage()).build(); // se ovan
        }

    }

    
    @POST
    @PermitAll
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/login")
    public Response loginUser(UserDto userDto){


        try {
            userService.loginUser(userDto);
        } catch ( IllegalArgumentException e ) {
            return Response.status(400).entity(e.getMessage()).build();
        }
       
        return Response.ok(userDto).build();
    }

}
