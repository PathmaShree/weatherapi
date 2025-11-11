package com.weather.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class WeatherDto {
  private Long id;
  private String cityName;
  private String temperature;
  private String weatherDesc;
//public WeatherDto(Long id, String cityName, String temperature, String weatherDesc) {
//	super();
//	this.id = id;
//	this.cityName = cityName;
//	this.temperature = temperature;
//	this.weatherDesc = weatherDesc;
//}
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
@Override
public String toString() {
	return "WeatherDto [id=" + id + ", cityName=" + cityName + ", temperature=" + temperature + ", weatherDesc="
			+ weatherDesc + "]";
}
	
}
