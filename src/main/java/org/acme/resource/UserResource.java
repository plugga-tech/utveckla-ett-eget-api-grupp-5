package org.acme.resource;

import org.acme.model.User;
import org.acme.model.UserDto;
import org.acme.service.UserService;
import org.bouncycastle.openssl.PasswordException;

import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/api/users")
public class UserResource {

    @Inject
    UserService userService;

    @Inject
    ResourceHelper res;

    /*
     * förväntar sig anrop i form av JSON
     * Typ:
     * {
     * "username": "namhär",
     * "password": "passwordhär"
     * }
     * 
     */
    @POST
    @Transactional
    @Produces(MediaType.TEXT_PLAIN)
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("/new-user")
    public Response newUser(UserDto userDto) {

        try {
            userService.createUser(userDto);
            return res.respond("User was successfully created. You can now log in to see your API key.");

        } catch (PasswordException e) {
            return res.respond(e);
        } catch (IllegalArgumentException e) {
            return res.respond(e);
        }

    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.TEXT_PLAIN)
    @Path("/get-key")
    public Response getKey(UserDto userDto) {
        try {
            String apiKey = userService.fetchApiKey(userDto.getUsername(), userDto.getPassword());
            return res.respond(apiKey);
        } catch (IllegalArgumentException e) {
            return res.respond(e);
        }

    }

    @POST
    @Consumes(MediaType.TEXT_PLAIN)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/find-user")
    public Response getUserByName(String username) {

        try {
            User user = userService.returnUser(username);
            return res.respond(user);
        } catch (IllegalArgumentException e) {
            return res.respond(e);
        }
    }
}
