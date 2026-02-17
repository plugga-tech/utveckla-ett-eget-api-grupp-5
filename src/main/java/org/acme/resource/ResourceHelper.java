package org.acme.resource;

import java.nio.file.AccessDeniedException;
import java.util.List;

import org.acme.model.HeroResponseDto;
import org.acme.model.User;
import org.bouncycastle.openssl.PasswordException;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.NoResultException;
import jakarta.ws.rs.NotFoundException;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.Response.Status;


/* ================================================================================================================ 
    Jag är riktigt nöjd med den här klassen.                                                                                 

    Tyckte det blev stökigt att ha typ 50 rader kod av bara responsmeddelanden, och massa duplicerade.               
    Insåg att formatet på "Status.BAD_REQUEST" (t.ex) liknar ju bara ett statiskt anrop till en konstant             
    i en annan klass. Och om det är så, borde jag kunna mappa till den dynamiskt.                                    
    Först gjorde jag en metod som tog in e.getMessage() och Status-konstanten, men kom på att samma logik            
    går att applicera genom att mappa exception-typen till rätt statuskod istället, och kunde smala av det ännu mer! 
    Så istället för att behöva skriva:                                                                               

    'return Response.status(Response.Status.ERROR_TYP).entity(e.getMessage()).build();' 
    
    vid varje felmeddelande, kan vi nu istället skriva:

    'return res.response(e);'

    Oavsett vilken typ av fel, eller felmeddelande det är, förutsatt att vi fångat den från ett exception ifrån en 
    anropad metod.

                                                                                                      /Joel       
   ================================================================================================================ */

@ApplicationScoped
public class ResourceHelper {


    public Response respond(String responseMsg){
        return Response.ok(responseMsg).build();
    }
    
    public Response respond(HeroResponseDto hero){
        return Response.ok(hero).build();
    }

    public Response respond(User user) {
        return Response.ok(user).build();
    }

    // Returnerar 'OK' resultat för listor (kör wildcard för att hantera olika typer (trots att det troligtvis är heroresponse i alla))
    public Response respond(List<?> responseMsg){
        return Response.ok(responseMsg).build();
    }

    public Response respond(Exception error) {
        return Response
                .status(selectStatusCode(error))
                .entity(error.getMessage())
                .build();
    }

    public Response respond(Exception error, String msg) {
        return Response
                .status(selectStatusCode(error))
                .entity(msg)
                .build();
    }


    private Status selectStatusCode(Exception error) {

          return switch (error) {
            case IllegalArgumentException   e -> Status.BAD_REQUEST;
            case PasswordException          e -> Status.BAD_REQUEST;
            case NotFoundException          e -> Status.NOT_FOUND;  // Ja, jag vet att vi hade kunnat skriva
            case NoResultException          e -> Status.NOT_FOUND; // båda på samma case, men snyggare såhär
            case AccessDeniedException      e -> Status.FORBIDDEN;
            default                           -> Status.INTERNAL_SERVER_ERROR;
        };

    }

    public Response respond(String msg, String error) {
        
        Status status = switch (error) {
            case "bad"      -> Status.BAD_REQUEST;
            case "notFound" -> Status.NOT_FOUND;
            default         -> Status.INTERNAL_SERVER_ERROR;
        };

        return Response 
                .status(status)
                .entity(msg)
                .build();
    }


}
