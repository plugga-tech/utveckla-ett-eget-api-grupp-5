
const btn = document.getElementById("testBtn")
const inputFld = document.getElementById("testInput")

btn.addEventListener("click", () => {
    /*   getHeroByName(inputFld.value) */
    /*   getAllHeroes()  */
         getUserHeroes()
    /*   createHero(inputFld.value) */
})




/* async function getUserHeroes() {
    const response = await fetch("http://localhost:8080/api/hero/get-all-heroes", {
        method: "GET",
        headers: {
            "X-API-KEY": "123456"
        }

    });
    const result = await response.json();
    console.log(result);
}
  */


/*   async function getAllHeroes() {
    const response = await fetch("http://localhost:8080/api/hero/get-all-heroes", {
        method: "GET",
    });
    const result = await response.json();
    console.log(result);
}
 */

/* async function deleteHero(id) {
    const response = await fetch(`http://localhost:8080/api/hero/delete/${id}`, {
        method: "DELETE",
        headers: {
            "X-API-KEY": "123456"   
        }
    });

    const result = await response.text();

    console.log(result)
}
 */


/*  async function getHeroByName(name) {
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
 */


/* 
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
} */