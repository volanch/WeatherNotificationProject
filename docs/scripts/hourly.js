$("#fetchHourly").click(() => {
    const city = $("#city").val().trim();
    if (!city) {
        alert("Please enter a city name first.");
        return;
    }

    fetch(`http://localhost:8080/weather/hourly/${city}`)
        .then(res => res.json())
        .then(hourlyData => {
            const tbody = $("#hourlyWeather");
            tbody.html("");

            hourlyData.forEach(hour => {
                const row = `<tr>
                    <td>${hour.date}</td>
                    <td>${hour.temperature}</td>
                    <td>${hour.apparentTemperature}</td>
                    <td>${hour.humidity}</td>
                    <td>${hour.windSpeed}</td>
                    <td>${hour.precipitation}</td>
                    <td>${hour.rain}</td>
                    <td>${hour.weatherCode}</td>
                </tr>`;
                tbody.append(row);
            });

            $("#hourlyWeatherTable").show();
        })
        .catch(err => console.error("Failed to fetch hourly weather:", err));
});
