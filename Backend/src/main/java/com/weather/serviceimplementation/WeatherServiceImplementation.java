package com.weather.serviceimplementation;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.weather.dto.WeatherDto;
import com.weather.entity.WeatherSearchHistory;
import com.weather.mapper.WeatherMapper;
import com.weather.repository.WeatherSearchHistoryRepository;
import com.weather.service.WeatherService;
import org.json.JSONObject;       
import org.json.JSONArray;        


@Component
public class WeatherServiceImplementation implements WeatherService {
	
	private final WeatherSearchHistoryRepository repo;
	private final RestTemplate restTemplate = new RestTemplate();
	
	@Value("${weather.api.key}")
	private String apiKey;
	
	public WeatherServiceImplementation(WeatherSearchHistoryRepository repo) {
		this.repo = repo;
	}

	@Override
	public WeatherDto fetchWeather(String city) {
		// TODO Auto-generated method stub
		String url = "https://api.openweathermap.org/data/2.5/weather?q=" 
	             + URLEncoder.encode(city, StandardCharsets.UTF_8) 
	             + "&appid=" + apiKey + "&units=metric";

	    try {
	    	String response = restTemplate.getForObject(url, String.class);
	    	JSONObject json = new JSONObject(response);
	    	json.getJSONObject("main");
	    	json.getJSONArray("weather");
	    	String temp = json.getJSONObject("main").get("temp").toString() + "°C";
	    	JSONArray weatherArray = json.getJSONArray("weather");
            String desc = weatherArray.getJSONObject(0).getString("description");
	    	WeatherSearchHistory entity = new WeatherSearchHistory();
	    	entity.setCityName(city);
	    	entity.setTemperature(temp);
	    	entity.setWeatherDesc(desc);
	    	WeatherSearchHistory saved = repo.save(entity);
	    	return WeatherMapper.mapToDto(saved);
	    }
		catch(Exception e) {
			 throw new RuntimeException("Failed to fetch weather for city: " + city, e);
		}
	}

	@Override
	public WeatherDto getWeatherById(Long id) {
		// TODO Auto-generated method stub
		WeatherSearchHistory entity = repo.findById(id)
				.orElseThrow(()-> new RuntimeException("Weather record not found for ID: " + id));
		return WeatherMapper.mapToDto(entity);
	}

	@Override
	public List<WeatherDto> getAllWeather() {
		// TODO Auto-generated method stub
		List<WeatherSearchHistory> all = repo.findAll();
		if(all.isEmpty()) throw new RuntimeException("No records found");
		return all.stream()
				.map(WeatherMapper::mapToDto)
				.collect(Collectors.toList());
	}

	@Override
	public WeatherDto updateWeather(Long id, WeatherDto d) {
		// TODO Auto-generated method stub
		WeatherSearchHistory entity = repo.findById(id)
				.orElseThrow(()->new RuntimeException("Weather record not found for ID: " + id));
		entity.setCityName(d.getCityName());
		entity.setTemperature(d.getTemperature());
		entity.setWeatherDesc(d.getWeatherDesc());
        WeatherSearchHistory updated = repo.save(entity);
		return WeatherMapper.mapToDto(updated);
	}

	@Override
	public WeatherDto partialUpdate(Long id, WeatherDto d) {
		// TODO Auto-generated method stub
		WeatherSearchHistory entity = repo.findById(id)
				.orElseThrow(() -> new RuntimeException("Weather record not found for ID: " + id));
		if(d.getCityName()!=null) entity.setCityName(d.getCityName());
		if(d.getTemperature()!=null) entity.setTemperature(d.getTemperature());
		if(d.getWeatherDesc()!=null) entity.setWeatherDesc(d.getWeatherDesc());
		WeatherSearchHistory partialUpdate = repo.save(entity);
		return WeatherMapper.mapToDto(partialUpdate);
	}

	@Override
	public void deleteWeatherHistory(Long id) {
		// TODO Auto-generated method stub
		 if (!repo.existsById(id))
	            throw new RuntimeException("Weather record not found for ID: " + id);
	        repo.deleteById(id);
	}
}
