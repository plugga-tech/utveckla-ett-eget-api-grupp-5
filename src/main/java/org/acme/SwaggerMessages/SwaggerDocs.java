package org.acme.SwaggerMessages;

public class SwaggerDocs {
    
    public static final String HERO_FETCH_ALL_HEROES_JAVASCRIPT_STRING =
    """
        Returns all heroes.

        Example JS:

        
        async function getAllHeroes(){
            const response = await fetch("http://localhost:8080/api/hero/get-all-heroes", {
                method: "GET",
                headers: {
                    "X-API-KEY": "123456"
                }
            });

            const result = await response.json();
            console.log(result);
        }
        ```
    """;
        
    public static final String HERO_DELETE_HERO_JAVASCRIPT_STRING = 
    """
        Deletes a hero by id.

        Example JS:
        
       async function deleteHero(id) {
    const response = await fetch(`http://localhost:8080/api/hero/${id}`,
        {
            method: "DELETE",
            headers: {
                "Content-Type": "application/json",
                "X-API-KEY": "123456",                
            },
            body: id,
        }
        );
        if (!response.ok) {  
        }
        }
    """;

    public static final String HERO_FETCH_HERO_BY_NAME_JAVASCRIPT_STRING =
       
    """
        Returns hero by name.

        Example JS:

        async function getHeroByName(name){
        const response = await fetch("http://localhost:8080/api/hero/get-hero-by-name", {
        method: "POST",
        headers: { 
            "Content-Type": "text/plain",
            "X-API-KEY": "123456" },  
        body: name      
        });
        const result = await response.json()
        console.log(result);
        }
    """;


    public static final String CREATE_NEW_HERO_STRING =
        """
            Creates a hero.
    
            Example JS:
    
            async function createHero(name){
            const response = await fetch("http://localhost:8080/api/hero/new-hero", {
            method: "POST",
            headers: { 
                "Content-Type": "application/json",
                "X-API-KEY": "123456" },  
            body: JSON.stringify({ 
                "name": "asdasdasd",
                "race": "Orc",
                "weapon": "SWORD",
                "heroClass": "WARRIOR"
             })
            });
            const result = await response.json();
            console.log(result);
        }
     ***

        
        Error Code: [ 200 ]         =         Successfully created hero
        Error Code: [ 500 ]         =         Server error when creating hero, check that predefined race and class are valid
        Error Code: [ 400 ]         =         Invalid hero data
    """;
}


