package com.weather.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.weather.dto.WeatherDto;
import com.weather.entity.WeatherSearchHistory;

@Service
public interface WeatherService {
//   WeatherSearchHistory fetchWeather(String city);
//   WeatherSearchHistory getWeatherById(Long id);
//   List<WeatherSearchHistory> getAllWeather();
//   WeatherSearchHistory updateWeather(Long id,WeatherSearchHistory e);
//   WeatherSearchHistory partialUpdate(Long id,WeatherSearchHistory e);
//   void deleteWeatherHistory(Long id);
	
	   WeatherDto fetchWeather(String city);
	   WeatherDto getWeatherById(Long id);
	   List<WeatherDto> getAllWeather();
	   WeatherDto updateWeather(Long id,WeatherDto d);
	   WeatherDto partialUpdate(Long id,WeatherDto d);
	   void deleteWeatherHistory(Long id);
}
