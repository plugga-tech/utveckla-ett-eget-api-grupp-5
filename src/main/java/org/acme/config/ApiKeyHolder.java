package org.acme.config;


import jakarta.enterprise.context.RequestScoped;

/* ================================================================= */
/* Använder denna istället för att lagra nyckeln i samma klass som   */
/* filtret. RequestScoped gör dessutom att varje request får sin     */
/* egen instans, så att inte två olika requests skriver över         */
/* varandras nyckel. Lite som ThreadLocal, typ.                      */
/* ================================================================= */

@RequestScoped
public class ApiKeyHolder {

    private String apiKey;

    public String getApiKey() {
        return apiKey;
    }

    public void setApiKey(String apiKey) {
        this.apiKey = apiKey;
    }

}
