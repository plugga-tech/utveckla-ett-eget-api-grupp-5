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
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/api/users")
public class UserResource {

    @Inject
    UserService userService;


    @GET
    @Produces(MediaType.TEXT_PLAIN)
    @RolesAllowed("user")
    @Path("/me")
    public Response showApiKey(UserDto userDto){

        String apiKey = userService.fetchApiKey(userDto);

        return Response.ok(apiKey).build();

    }



    /*
    förväntar sig anrop i form av JSON
    Typ: 
    {
        "username": "namhär",
        "password": "passwordhär"
    }
    */
    @POST
    @Transactional
    @Produces(MediaType.TEXT_PLAIN)
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("/new-user")
    public Response newUser (UserDto userDto) {

        try {
            userService.createUser(userDto);
        } catch (PasswordException e) {
            return Response.status(400, e.getMessage()).build();
        } catch (IllegalArgumentException e) {
            return Response.status(400, e.getMessage()).build();
        }
        
        return Response.ok("User created. Log in to see to see your API key.").build();

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
            return Response.status(400, "Wrong username or password").build();
        }
       
        return Response.ok(userDto).build();
    }

}
