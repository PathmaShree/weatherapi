package com.weather.mapper;

import com.weather.dto.WeatherDto;
import com.weather.entity.WeatherSearchHistory;

public class WeatherMapper {

	public static WeatherDto mapToDto(WeatherSearchHistory entity) {                           // entity -> dto (sending response)
		WeatherDto dto = new WeatherDto();
		dto.setId(entity.getId());
		dto.setCityName(entity.getCityName());
		dto.setTemperature(entity.getTemperature());
		dto.setWeatherDesc(entity.getWeatherDesc());
		return dto;
		}
	public static WeatherSearchHistory mapToEntity(WeatherDto dto) {                           //dto -> entity(when saving request)
		WeatherSearchHistory entity = new WeatherSearchHistory();
		entity.setId(dto.getId());
		entity.setCityName(dto.getCityName());
		entity.setTemperature(dto.getTemperature());
		entity.setWeatherDesc(dto.getWeatherDesc());
		return entity;
	}
	
}

