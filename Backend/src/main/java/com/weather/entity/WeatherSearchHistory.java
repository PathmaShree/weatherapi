package com.weather.entity;

import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

@Entity
@Table(name = "WEATHER_SEARCH_HISTORY")
public class WeatherSearchHistory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
    
    @Column(name = "CITY_NAME", nullable = false, length = 100)
    private String cityName;
    
    @Column(name = "TEMPERATURE", length = 50)
    private String temperature;
    
    @Column(name = "WEATHER_DESC", length = 255)
    private String weatherDesc;
    
    @CreationTimestamp
    @Column(name = "SEARCHED_AT", updatable = false)
    private LocalDateTime searchedAt;
    
    @UpdateTimestamp
    @Column(name = "updated_at") 
    private LocalDateTime updatedAt;
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public String getTemperature() {
        return temperature;
    }

    public void setTemperature(String temperature) {
        this.temperature = temperature;
    }

    public String getWeatherDesc() {
        return weatherDesc;
    }

    public void setWeatherDesc(String weatherDesc) {
        this.weatherDesc = weatherDesc;
}
}
