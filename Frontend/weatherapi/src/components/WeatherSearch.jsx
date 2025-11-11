import React, { useState } from "react";
import axios from "axios";
import WeatherHistory from "./WeatherHistory"; // ✅ import your history component

function WeatherSearch() {
  const [city, setCity] = useState("");
  const [weather, setWeather] = useState(null);
  const [loading, setLoading] = useState(false);
  const [error, setError] = useState("");
  const [showHistory, setShowHistory] = useState(false);

  const formatError = (err) => {
    if (!err) return "Unknown error";
    if (err.response?.data) {
      if (typeof err.response.data === "string") return err.response.data;
      if (err.response.data.message) return err.response.data.message;
      return JSON.stringify(err.response.data);
    }
    return err.message || "Failed request";
  };

  // Fetch weather
  const fetchWeather = async () => {
    try {
      setLoading(true);
      const res = await axios.get(
        `http://localhost:8083/weatherapi/fetch?city=${encodeURIComponent(city)}`
      );
      setWeather(res.data);
      setError("");
    } catch (err) {
      console.error(err);
      setError(formatError(err));
    } finally {
      setLoading(false);
    }
  };

  // ✅ When showHistory is true → show WeatherHistory component
  if (showHistory) {
    return (
      <div className="app-container">
        <button onClick={() => setShowHistory(false)}>🔙 Back to Search</button>
        <WeatherHistory /> {/* ✅ Your existing WeatherHistory component */}
      </div>
    );
  }

  return (
    <div className="app-container">
      <h1>🌤️ Weather App</h1>

      <div className="weather-search">
        <input
          type="text"
          placeholder="Enter a city"
          value={city}
          onChange={(e) => setCity(e.target.value)}
        />

        {/* ✅ Added your button group exactly here */}
        <div className="button-group">
          <button onClick={fetchWeather}>Get Weather</button>
          <button className="history-btn" onClick={() => setShowHistory(true)}>
            Show History 📜
          </button>
        </div>

        {loading && <p>Fetching weather...</p>}
        {error && <p className="error">{error}</p>}

        {weather && (
          <div className="weather-result">
            <h3>{weather.cityName}</h3>
            <p>🌡 Temperature: {weather.temperature}</p>
            <p>☁️ Condition: {weather.weatherDesc}</p>
          </div>
        )}
      </div>
    </div>
  );
}

export default WeatherSearch;
