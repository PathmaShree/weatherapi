import React, { useState, useEffect } from "react";
import axios from "axios";
import "../App.css";

function WeatherHistory() {
  const [history, setHistory] = useState([]);
  const [error, setError] = useState("");
  const [editingId, setEditingId] = useState(null);
  const [editData, setEditData] = useState({ cityName: "", temperature: "", weatherDesc: "" });

  // Fetch all weather history
  const fetchHistory = async () => {
    try {
      const res = await axios.get("http://localhost:8083/weatherapi/all");
      setHistory(res.data);
    } catch (err) {
      console.error(err);
      setError("Failed to fetch history");
    }
  };

  useEffect(() => {
    fetchHistory();
  }, []);

  // Start editing a record
  const startEditing = (w) => {
    setEditingId(w.id);
    setEditData({
      cityName: w.cityName || "",
      temperature: w.temperature ? w.temperature.replace("°C", "") : "",
      weatherDesc: w.weatherDesc || "",
    });
    setError("");
  };

  // Cancel editing
  const cancelEditing = () => {
    setEditingId(null);
    setEditData({ cityName: "", temperature: "", weatherDesc: "" });
    setError("");
  };

  // Helper to format error messages
  const formatError = (err) => {
    if (!err) return "Unknown error";
    if (err.response?.data) {
      if (typeof err.response.data === "string") return err.response.data;
      if (err.response.data.message) return err.response.data.message;
      return JSON.stringify(err.response.data);
    }
    return err.message || "Failed request";
  };

  // Full update (PUT)
  const saveEdit = async (id) => {
    try {
      if (!editData.cityName || !editData.temperature || !editData.weatherDesc) {
        setError("All fields are required for full update");
        return;
      }

      await axios.put(`http://localhost:8083/weatherapi/update/${id}`, {
        ...editData,
        temperature: editData.temperature + "°C",
      });

      setEditingId(null);
      setError("");
      fetchHistory();
    } catch (err) {
      console.error(err);
      setError(formatError(err));
    }
  };

  // Partial update (PATCH)
  const patchEdit = async (id) => {
    try {
      await axios.patch(`http://localhost:8083/weatherapi/partialUpdate/${id}`, {
        ...editData,
        temperature: editData.temperature ? editData.temperature + "°C" : undefined,
      });

      setEditingId(null);
      setError("");
      fetchHistory();
    } catch (err) {
      console.error(err);
      setError(formatError(err));
    }
  };

  // Delete record
  const deleteWeather = async (id) => {
    try {
      await axios.delete(`http://localhost:8083/weatherapi/delete/${id}`);
      fetchHistory();
    } catch (err) {
      console.error(err);
      setError(formatError(err));
    }
  };

  return (
    <div className="weather-history">
      <h2>Weather History</h2>
      {error && <p className="error">{error}</p>}

      {history.length === 0 ? (
        <p>No history found</p>
      ) : (
        
        <div className="weather-history-container">
          <div className="history-list">
            {history.map((w) => (
              <div key={w.id} className="history-item">
                {editingId === w.id ? (
                  <div className="edit-fields">
                    <input
                      type="text"
                      placeholder="City Name"
                      value={editData.cityName}
                      onChange={(e) => setEditData({ ...editData, cityName: e.target.value })}
                    />
                    <input
                      type="number"
                      placeholder="Temperature (°C)"
                      value={editData.temperature}
                      onChange={(e) => setEditData({ ...editData, temperature: e.target.value })}
                    />
                    <input
                      type="text"
                      placeholder="Weather Description"
                      value={editData.weatherDesc}
                      onChange={(e) => setEditData({ ...editData, weatherDesc: e.target.value })}
                    />
                  </div>
                ) : (
                  <div className="item-info">
                    <span>{w.cityName}</span> | <span>{w.temperature}</span> | <span>{w.weatherDesc}</span>
                  </div>
                )}

                <div className="item-actions">
                  {editingId === w.id ? (
                    <>
                      <button type="button" onClick={() => saveEdit(w.id)} className="edit-btn">
                        Update
                      </button>
                      <button type="button" onClick={() => patchEdit(w.id)} className="patch-btn">
                        Partial Update
                      </button>
                      <button type="button" onClick={cancelEditing} className="cancel-btn">
                        Cancel
                      </button>
                    </>
                  ) : (
                    <>
                      <button type="button" onClick={() => startEditing(w)} className="edit-btn">
                        Edit
                      </button>
                      <button type="button" onClick={() => deleteWeather(w.id)} className="delete-btn">
                        Delete
                      </button>
                    </>
                  )}
                </div>
              </div>
            ))}
          </div>
        </div>
      )}
    </div>
  );
}

export default WeatherHistory;
