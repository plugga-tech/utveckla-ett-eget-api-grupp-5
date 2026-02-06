package org.acme.resource;


import java.util.List;

import org.acme.model.HeroDto;
import org.acme.model.HeroResponseDto;
import org.acme.service.HeroService;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;

import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.PATCH;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DELETE;
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
    @Path("/new-hero")
    public Response newHero(HeroDto heroDto){

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
    @Operation(summary = "Creates a new hero", description = "Creates a new hero in the system, check Example Value for structure.")
    @APIResponse(responseCode = "200",description = "Successfully created hero")
    @APIResponse(responseCode = "500", description = "Server error when creating hero, check that predefined race and class are valid")
    @Path("/get-all-heroes")
    public List<HeroResponseDto> getAllHeroes(){

        return heroService.getAllHeroes();

    }

    //Uppdaterar en hero baserat på id
    @PATCH
    @Path("/{id}")
    @Transactional
    public Response updateHero(@PathParam("id") int id, HeroDto heroDto){

        //Anropa service för att uppdatera hero
        HeroResponseDto hero = heroService.updateHero(id, heroDto);

        // Om hero inte finns, returnera 404
        if (hero == null){
            return Response
                .status(Response.Status.NOT_FOUND)
                .entity("Hero not found")
                .build();
        }

        //Returnera uppdaterad hero
        return Response.ok(hero).build();
    }

    //Raderar en hero baserat på id
    @DELETE
    @Path("/{id}")
    @Transactional
    public Response deleteHero(@PathParam("id") int id){
        // Exempel: /api/hero/get-hero-by-class/MAGE
    // Detta visar alla hjältar som har klassen MAGE
    // det går att byta ut MAGE mot någon av de andra klasserna: WARRIOR, ROGUE, PALADIN
    // för att visa hjältar av den klassen istället.
    
    
    boolean deleted = heroService.deleteHero(id);
    
    //om hero inte finns, returnera 404
    if(!deleted){
        return Response
        .status(Response.Status.NOT_FOUND)
        .entity("Hero not found")
        .build();
    }
    
    // Om hero har tagits bort, returnera 204 No Content
    return Response.noContent().build();
}
@GET
@Path("/get-hero-by-class/{heroClass}")
public List<HeroResponseDto> getHeroesByClass(@jakarta.ws.rs.PathParam("heroClass") String heroClass){

    return heroService.getHeroesByClass(heroClass);

}
}




