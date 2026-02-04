
const regBtn = document.getElementById("regBtn");
const getKeyBtn = document.getElementById("getKeyBtn");

// Den här metoden hanterar registrering av ny användare och returnerar användarens API nyckel
if (regBtn) {
    regBtn.addEventListener("click", async () => {
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
}

if (getKeyBtn) {
    getKeyBtn.addEventListener("click", async () => {
        const username = document.getElementById("usernameForKey").value;
        const password = document.getElementById("passwordForKey").value;

        const response = await fetch("http://localhost:8080/api/users/get-key", {
            method: "POST",
            headers: { "Content-Type": "application/json" },
            body: JSON.stringify({ username, password })
        });

        if (response.ok) {
        const key = await response.text();
        document.getElementById("apiKeyResult").textContent = 'Your API Key: ' + key;
        } else {
        const error = await response.text();
        document.getElementById("apiKeyResult").textContent = error;
    }

    });
}