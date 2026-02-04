
// Den  här metoden hanterar registrering av ny användare och returnerar användarens API nyckel
document.getElementById("regBtn").addEventListener("click", async () => {
    const username = document.getElementById("regUser").value;
    const password = document.getElementById("regPw").value;
    
    const response = await fetch("http://localhost:8080/api/users/new-user", {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify({ username, password })
    });
    
    const result = await response.text();
    document.getElementById("regResult").textContent = result;
});