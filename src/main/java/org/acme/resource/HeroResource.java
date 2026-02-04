package org.acme.resource;


import java.util.List;

import org.acme.model.HeroDto;
import org.acme.model.HeroResponseDto;
import org.acme.service.HeroService;

import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.MediaType;


@Path("/api/hero")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class HeroResource {

    @Inject
    HeroService heroService;

    @Inject
    EntityManager em;
    
    @POST
    @Transactional
    public Response postHero(HeroDto heroDto){

        try {
            
            HeroResponseDto hero = heroService.createHero(heroDto);
            return Response
                .status(Response.Status.CREATED)
                .entity(hero)
                .build();

        } catch (IllegalArgumentException e) {
            
            return Response
            .status(Response.Status.INTERNAL_SERVER_ERROR)
            .entity("Error creating hero")
            .build();
        }

    }

    @GET
    @Path("/all")
    public List<HeroResponseDto> getAllHeroes(){

        return heroService.getAllHeroes();

    }

}
