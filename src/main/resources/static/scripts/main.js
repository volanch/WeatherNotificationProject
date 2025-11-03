let stompClient = null;

function setConnected(connected) {
    $("#connect").prop("disabled", connected);
    $("#disconnect").prop("disabled", !connected);
    if (connected) $("#conversation").show();
    else $("#conversation").hide();
    $("#weather").html("");
}

function connect() {
    const city = $("#city").val().trim();
    if (!city) {
        alert("Please enter a city name first.");
        return;
    }

    stompClient = new StompJs.Client({
        brokerURL: "ws://localhost:8080/ws-weather",
    });

    stompClient.onConnect = (frame) => {
        setConnected(true);
        console.log("Connected: " + frame);

        stompClient.subscribe("/topic/weather", (message) => {
            const data = JSON.parse(message.body);
            showWeather(data);
        });

        fetch(`http://localhost:8080/weather/${city}`)
            .then((res) => res.json())
            .then((data) => showWeather(data))
            .catch((err) => console.error("REST API error:", err));
    };

    stompClient.onWebSocketError = (error) => {
        console.error("WebSocket Error:", error);
    };

    stompClient.onStompError = (frame) => {
        console.error("Broker Error:", frame.headers["message"]);
        console.error("Details:", frame.body);
    };

    stompClient.activate();
}

function disconnect() {
    if (stompClient) stompClient.deactivate();
    setConnected(false);
    console.log("Disconnected");
}

function showWeather(data) {
    window.currentWeather = data;
    $("#weather").html(`
        <tr><td>
            <strong>City:</strong> ${data.city} <br>
            <strong>Date:</strong> ${data.date} <br>
            <strong>Temperature:</strong> ${data.temperature}°C <br>
            <strong>Apparent Temperature:</strong> ${data.apparentTemperature}°C <br>
            <strong>Humidity:</strong> ${data.humidity}% <br>
            <strong>Wind:</strong> ${data.windSpeed} km/h <br>
            <strong>Precipitation:</strong> ${data.precipitation} mm <br>
            <strong>Rain:</strong> ${data.rain} mm <br>
            <strong>Weather Code:</strong> ${data.weatherCode}
        </td></tr>
    `);
}

$(function () {
    $("form").on("submit", (e) => e.preventDefault());
    $("#connect").click(() => connect());
    $("#disconnect").click(() => disconnect());

    $("#sendNotification").click(() => {
        const recipient = $("#recipient").val().trim();
        const type = $("#type").val(); // gmail or outlook

        if (!recipient) {
            alert("Please enter a recipient email.");
            return;
        }

        if (!window.currentWeather) {
            alert("Weather data not available. Connect to a city first.");
            return;
        }

        // Build weather message
        const data = window.currentWeather;
        const message = `
City: ${data.city}
Date: ${data.date}
Temperature: ${data.temperature}°C
Apparent Temperature: ${data.apparentTemperature}°C
Humidity: ${data.humidity}%
Wind: ${data.windSpeed} km/h
Precipitation: ${data.precipitation} mm
Rain: ${data.rain} mm
Weather Code: ${data.weatherCode}
        `;

        fetch(`http://localhost:8080/notify?type=${type}&recipient=${recipient}&message=${encodeURIComponent(message)}`, {
            method: 'POST'
        })
            .then(res => {
                if (res.ok) {
                    alert(`Notification sent via ${type}`);
                } else {
                    alert(`Failed to send notification. Status: ${res.status}`);
                }
            })
            .catch(err => {
                console.error("Notification error:", err);
                alert("Failed to send notification. See console for details.");
            });
    });
});
