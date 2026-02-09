package org.acme.SwaggerMessages;

public class SwaggerDocs {
    
    public static final String HERO_FETCH_ALL_HEROES_JAVASCRIPT_STRING =
    """
        Returns all heroes.

        Example JS:

            async function getAllHeroes(){
                    const response = await fetch("http://localhost:8080/api/hero/get-all-heroes", {
                        method: "GET",
                    });

                    const result = await response.json();
                    console.log(result);
                }
        }
        ***

        Error Code: [ 200 ]         =         Successfully returned all heroes
        Error Code: [ 401 ]         =         API key is missing
        Error Code: [ 403 ]         =         Invalid API key
        Error Code: [ 404 ]         =         No heroes found
    """;
    
    public static final String HERO_DELETE_HERO_JAVASCRIPT_STRING = 
    """
        Deletes a hero by id.

        Example JS:

        async function deleteHero(id) {
                const response = await fetch(`http://localhost:8080/api/hero/delete/${id}`, {
                    method: "DELETE",
                    headers: {
                        "X-API-KEY": "123456"
                    }
                });

                const result = await response.text();

                if (!response.ok) {
                    console.log(result);
                } else {

                console.log(result)
            }
        }    

        ***

        Error Code: [ 200 ]   =         Successfully deleted hero
        Error Code: [ 400 ]   =         Invalid hero ID
        Error Code: [ 401 ]   =         API key is missing
        Error Code: [ 403 ]   =         Invalid API key
        Error Code: [ 404 ]   =         Hero not found
    """;

    public static final String HERO_FETCH_HERO_BY_NAME_JAVASCRIPT_STRING =
    """
        Returns hero by name.

        Example JS:

        async function getHeroByName(name) {
            const response = await fetch(`http://localhost:8080/api/hero/get-hero-by-name/${name}`, {
                method: "GET",
                headers: {
                    "X-API-KEY": "123456"
                }
            });

            if (!response.ok) {
                const errorText = await response.text();
                console.log(errorText)
                return;
            } else {
                const result = await response.json();
                console.log(result);
            }

        }
            

        ***

        Error Code: [ 200 ]       =         Successfully returned hero
        Error Code: [ 400 ]       =         Invalid hero name
        Error Code: [ 401 ]       =         API key is missing
        Error Code: [ 403 ]       =         Invalid API key
        Error Code: [ 404 ]       =         Hero not found
    """;


    public static final String CREATE_NEW_HERO_STRING =
        """
        Creates a hero.

        Example JS:

            async function createHero(name) {
                const response = await fetch("http://localhost:8080/api/hero/new-hero", {
                    method: "POST",
                    headers: {
                        "Content-Type": "application/json",
                        "X-API-KEY": "123456"
                    },
                    body: JSON.stringify({
                        "name": name,
                        "race": "Orc",
                        "weapon": "SWORD",
                        "heroClass": "WARRIOR"
                    })
                });

                if (!response.ok) {
                    const error = await response.text();
                    console.log(error)
                } else {
                    const result = await response.json();
                    console.log(result);
                }

            }
     ***

        
        Error Code: [ 200 ]         =         Successfully created hero
        Error Code: [ 500 ]         =         Server error when creating hero, check that predefined race and class are valid
        Error Code: [ 400 ]         =         Invalid hero data
        Error Code: [ 401 ]         =         API key is missing
        Error Code: [ 403 ]         =         Invalid API key
    """;

    public static final String HERO_GET_USER_HEROES_STRING =
    """
            Returnspero  all heroes owned by the user.

            Example JS:

            async function getUserHeroes(){
                const response = await fetch("http://localhost:8080/api/hero/get-user-heroes", {
                    method: "GET",
                    headers: {
                        "X-API-KEY": "123456"
                    }
                });

                const result = await response.json();
                console.log(result);
            }

            ***

            Error Code: [ 200 ]       =         Successfully returned user heroes
            Error Code: [ 401 ]       =         API key is missing
            Error Code: [ 403 ]       =         Invalid API key
            Error Code: [ 404 ]       =         No heroes found for this user
    """;



    public static final String HERO_GET_BY_RACE_STRING = 
    """
        Returns all heroes by race.

        Example JS:

        async function getHeroesByRace(race){
            const response = await fetch(`http://localhost:8080/api/hero/get-heroes-by-race/${race}`, {
                method: "GET",
                headers: {
                    "X-API-KEY": "123456"
                }
            });

            if (!response.ok) {
                const error = await response.text();
                console.log(error);
            } else {
                const result = await response.json();
                console.log(result);
            }
        }

        ***

        Error Code: [ 200 ]       =         Successfully returned heroes
        Error Code: [ 400 ]       =         Invalid race
        Error Code: [ 401 ]       =         API key is missing
        Error Code: [ 403 ]       =         Invalid API key
        Error Code: [ 404 ]       =         No heroes found for this race
    """;

    public static final String HERO_UPDATE_HERO_STRING =
    """
        Updates a hero.

        Example JS:


        async function updateHero(weapon){
            const response = await fetch("http://localhost:8080/api/hero/update-hero", {
                method: "PATCH",
                headers: { 
                    "Content-Type": "application/json",
                    "X-API-KEY": "123456" 
                },  
                body: JSON.stringify({ 
                    "name": "Gronk",
                    "race": "Elf",
                    "weapon": weapon,
                    "heroClass": "WARRIOR"
                })
            });

            if (response.ok) {
                const result = await response.json();
                console.log(result);
            } else {
                const error = await response.text()
                console.log(error)
            }

        }



        ***

        Error Code: [ 200 ]       =         Successfully updated hero
        Error Code: [ 400 ]       =         Invalid hero data
        Error Code: [ 401 ]       =         API key is missing
        Error Code: [ 403 ]       =         Invalid API key
        Error Code: [ 404 ]       =         Hero not found
        Error Code: [ 500 ]       =         Server error when updating hero
    """;

    public static final String HERO_GET_BY_WEAPON_STRING = 
    """
        Returns all heroes by weapon.

        Example JS:

        async function getHeroesByWeapon(weapon){
            const response = await fetch(`http://localhost:8080/api/hero/get-heroes-by-weapon/${weapon}`, {
                method: "GET",
                headers: {
                    "X-API-KEY": "123456"
                }
            });

            if (response.ok) {
                const result = await response.json();
                console.log(result);
            } else {
                const error = await response.text()
                console.log(error)
            }
        }

        ***

        Error Code: [ 200 ]       =         Successfully returned heroes
        Error Code: [ 400 ]       =         Invalid weapon
        Error Code: [ 401 ]       =         API key is missing
        Error Code: [ 403 ]       =         Invalid API key
        Error Code: [ 404 ]       =         No heroes found for this weapon
    """;

    public static final String HERO_GET_BY_ID_STRING = 
    """
        Returns a hero by id.

        Example JS:

        async function getHeroById(id){
            const response = await fetch(`http://localhost:8080/api/hero/get-hero-by-id/${id}`, {
                method: "GET",
                headers: {
                    "X-API-KEY": "123456"
                }
            });

            if (response.ok) {
            const result = await response.json();
            console.log(result);
            } else {
            const error = await response.text();
            console.log(error)
            }
        }

        ***

        Error Code: [ 200 ]       =         Successfully returned hero
        Error Code: [ 400 ]       =         Invalid hero id
        Error Code: [ 401 ]       =         API key is missing
        Error Code: [ 403 ]       =         Invalid API key
        Error Code: [ 404 ]       =         Hero not found
    """;

    public static final String HERO_GET_BY_CLASS_STRING = 
    """
        Returns all heroes by class.

        Example JS:

        async function getHeroesByClass(heroClass){
            const response = await fetch(`http://localhost:8080/api/hero/get-heroes-by-class/${heroClass}`, {
                method: "GET",
                headers: {
                    "X-API-KEY": "123456"
                }
            });

            if (response.ok) {
                const result = await response.json();
                console.log(result);
            } else {
                const error = await response.text()
                console.log(error)
            }
        }

        ***

        Error Code: [ 200 ]       =         Successfully returned heroes
        Error Code: [ 400 ]       =         Invalid hero class
        Error Code: [ 401 ]       =         API key is missing
        Error Code: [ 403 ]       =         Invalid API key
        Error Code: [ 404 ]       =         No heroes found with this class
    """;
}


