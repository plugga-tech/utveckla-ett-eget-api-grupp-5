package org.acme.config;

import java.io.IOException;

import org.acme.model.User;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.ws.rs.container.ContainerRequestContext;
import jakarta.ws.rs.container.ContainerRequestFilter;
import jakarta.ws.rs.container.PreMatching;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.Provider;


/* ===================== API Key Filter ============================ */
/* Den här klassen fungerar precis som en interceptor i spring boot. */
/* ContainerRequestFilter är ett interface som låter oss fånga upp   */
/* inkommande requests innan de når sina respektive endpoints.       */
/* ================================================================= */

/* 

Steg för steg:
 * 1. Varje HTTP-request går genom detta filter
 * 2. Filtret kollar om requesten går till /api/users (öppet) → släpp igenom
 * 3. Annars kräver vi header: X-API-KEY
 * 4. Om header saknas → returnera 401 Unauthorized
 * 5. Om header finns men nyckeln är ogiltig → returnera 403 Forbidden
 * 6. Om nyckeln är giltig → requesten fortsätter till endpoint

 Mer info:
 https://docs.oracle.com/javaee/7/api/javax/ws/rs/container/ContainerRequestContext.html
 https://payara.fish/blog/intercepting-rest-requests-with-jakarta-rest-request-filters/

*/

@Provider     // <-- Gör att quarkus hittar filtret automatiskt och applicerar det på inkommande requests
@PreMatching // <-- Gör att filtret körs innan requesten matchas mot en endpoint
@ApplicationScoped
public class ApiKeyFilter implements ContainerRequestFilter {

    @Inject
    EntityManager em;

    @Inject
    ApiKeyHolder apiKeyHolder;



    @Override 
    public void filter(ContainerRequestContext requestContext) throws IOException {
        String path = requestContext.getUriInfo().getPath(); // Hämtar den inkommande requestens path

        // Låt alla requests till /api/users passera utan API key kontroll
        if (path.startsWith("/api/users") || path.startsWith("/api/hero/get-all-heroes")) {
            return;
        }

        String apiKey = requestContext.getHeaderString("X-API-KEY"); // Hämtar API key från headern

        // Lägger till apikey i en egen klass för att användas i nödvändiga klasser
        apiKeyHolder.setApiKey(apiKey);

        // Om ingen API key finns i headern, avbryt requesten med 401 Unauthorized
        if (apiKey == null || apiKey.isEmpty()) {
            requestContext.abortWith(
                    Response.status(401)
                            .entity("API key is missing.")
                            .build());
            return;
        }

        // Om API key finns men är ogiltig, avbryt requesten med 403 Forbidden
        if (invalidApiKey(apiKey)) {
            requestContext.abortWith(
                    Response.status(403)
                            .entity("Invalid API key.")
                            .build());
            return;
        }

        // Om API key är giltig, låt requesten fortsätta till endpointen

    }



    // Helper-metod för att kontrollera om API key är giltig
    private boolean invalidApiKey(String apiKey) {
        return em.createQuery("SELECT u FROM User u WHERE u.apiKey = :apiKey", User.class)
                .setParameter("apiKey", apiKey)
                .getResultList()
                .isEmpty();
    }

}
