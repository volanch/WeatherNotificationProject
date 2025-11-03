function showManualWeather(data) {
    $("#manualWeather").html(`
        <tr>
            <td>${data.city}</td>
            <td>${data.temperature}°C</td>
            <td>${data.apparentTemperature}°C</td>
            <td>${data.humidity}%</td>
            <td>${data.windSpeed} km/h</td>
            <td>${data.precipitation} mm</td>
            <td>${data.rain} mm</td>
            <td>${data.weatherCode}</td>
        </tr>
    `);
}

$("#updateManualWeather").click(() => {
    const city = $("#manual-city").val().trim();
    if (!city) { alert("Enter city"); return; }

    const data = {
        temperature: parseFloat($("#manual-temp").val()),
        apparentTemperature: parseFloat($("#manual-apparent").val()),
        humidity: parseInt($("#manual-humidity").val()),
        windSpeed: parseFloat($("#manual-wind").val()),
        precipitation: parseFloat($("#manual-precip").val()),
        rain: parseFloat($("#manual-rain").val()),
        weatherCode: $("#manual-code").val()
    };

    fetch(`http://localhost:8080/manual-weather/set`, {
        method: 'POST',
        headers: {'Content-Type': 'application/json'},
        body: JSON.stringify(data)
    })
        .then(res => res.text())
        .then(msg => {
            alert(msg);
            return fetch(`http://localhost:8080/manual-weather/get?city=${encodeURIComponent(city)}`);
        })
        .then(res => res.json())
        .then(showManualWeather)
        .catch(err => console.error("Error updating manual weather:", err));
});
