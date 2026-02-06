package org.acme.resource;


import java.util.List;

import org.acme.SwaggerMessages.SwaggerDocs;
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
    @Operation(summary = "Creates a new hero", description = "Creates a new hero in the system, check Example Value for structure.")
   @APIResponse(
    responseCode = "200", description = SwaggerDocs.CREATE_NEW_HERO_STRING)
    @Path("/new-hero")
    public Response newHero(HeroDto heroDto){

        // Kontrollera om hero är null, vilket kan hända om användaren inte skickar data eller skickar fel format
        if (heroDto == null) {
            return Response
                .status(Response.Status.BAD_REQUEST)
                .entity("Invalid hero data")
                .build();
        }

        // Kontrollera om hero namn finns.
        if (heroDto.getName() == null || heroDto.getName().isEmpty()) {
            return Response
                .status(Response.Status.BAD_REQUEST)
                .entity("Hero name is required")
                .build();
        }

        // Kontrollera om hero race finns.
        if (heroDto.getRace() == null || heroDto.getRace().isEmpty()) {
            return Response
                .status(Response.Status.BAD_REQUEST)
                .entity("Hero race is required")
                .build();
        }

        //kontrollera om hero class finns.
        if (heroDto.getHeroClass() == null || heroDto.getHeroClass().isEmpty()) {
            return Response
                .status(Response.Status.BAD_REQUEST)
                .entity("Hero class is required")
                .build();
        }

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
    @APIResponse(
    responseCode = "JAVASCRIPT EXAMPLE",
    description = SwaggerDocs.HERO_FETCH_ALL_HEROES_JAVASCRIPT_STRING
)
    @Path("/get-all-heroes")
    public List<HeroResponseDto> getAllHeroes(){
        return heroService.getAllHeroes();
    }

    @POST
    @Consumes(MediaType.TEXT_PLAIN)
    @APIResponse(
    responseCode = "JAVASCRIPT EXAMPLE",
    description = SwaggerDocs.HERO_FETCH_HERO_BY_NAME_JAVASCRIPT_STRING)
    @Path("/get-hero-by-name")
    public Response getHeroByName(String heroName){

        // Kontrollera om hero namn blev skrivet
        if (heroName == null || heroName.isEmpty()) {
            return Response
                .status(Response.Status.BAD_REQUEST)
                .entity("Hero name is required")
                .build();
        }

        try {
            HeroResponseDto responseDto =  heroService.getHeroByName(heroName);
            return Response.ok(responseDto).build();
        } catch (IllegalArgumentException e) {
            return Response
                .status(Response.Status.NOT_FOUND)
                .entity("Hero not found")
                .build();
        }

    }

    //Uppdaterar en hero baserat på id
    @PATCH
    @Path("/update-hero")
    @Transactional
    public Response updateHero(HeroDto heroDto){

        // kontrollera att data har skickats. 
        if (heroDto == null) {
            return Response
                .status(Response.Status.BAD_REQUEST)
                .entity("No hero data provided")
                .build();
        }

        // Kontrollera att namn finns.
        if (heroDto.getName() == null || heroDto.getName().isEmpty()) {
            return Response
                .status(Response.Status.BAD_REQUEST)
                .entity("Hero name is required")
                .build();
        }

        //Anropa service för att uppdatera hero
        HeroResponseDto hero = heroService.updateHero(heroDto);

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
    @APIResponse(
        responseCode = "JAVASCRIPT EXAMPLE",
        description = SwaggerDocs.HERO_DELETE_HERO_JAVASCRIPT_STRING
    )
    @Path("/{id}")
    @Transactional
    public Response deleteHero(@PathParam("id") int id){
        
    
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
        
    // Exempel: /api/hero/get-hero-by-class/MAGE
    // Detta visar alla hjältar som har klassen MAGE
    // det går att byta ut MAGE mot någon av de andra klasserna: WARRIOR, ROGUE, PALADIN
    // för att visa hjältar av den klassen istället.
    
@GET
@Path("/get-hero-by-class/{heroClass}")
public List<HeroResponseDto> getHeroesByClass(@PathParam("heroClass") String heroClass){

    return heroService.getHeroesByClass(heroClass);

}
}




