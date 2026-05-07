async function login() {

    const username = document.getElementById("username").value;
    const password = document.getElementById("password").value;

    const response = await fetch("http://localhost:8080/auth/login", {
        method: "POST",
        headers: {
            "Content-Type": "application/json"
        },
        body: JSON.stringify({
            username: username,
            password: password
        })
    });

    const data = await response.text();

    if (response.ok) {

        // ✅ Save token
        localStorage.setItem("token", data);

        // ✅ Go dashboard
        window.location.href = "dashboard.html";

    } else {
        document.getElementById("message").innerText = data;
    }
}