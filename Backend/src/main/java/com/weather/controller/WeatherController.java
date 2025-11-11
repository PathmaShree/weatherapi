package com.weather.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.weather.dto.WeatherDto;
import com.weather.entity.WeatherSearchHistory;
import com.weather.serviceimplementation.WeatherServiceImplementation;

@CrossOrigin(origins = "http://localhost:5173")
@RestController
@RequestMapping("/weatherapi")
public class WeatherController {
	private final WeatherServiceImplementation i;
	
	public WeatherController(WeatherServiceImplementation i) {
		this.i = i;
	}
	
	@GetMapping("/fetch")
	public ResponseEntity<?> fetchingWeather(@RequestParam String city){
		try {
			WeatherDto dto = i.fetchWeather(city);
			return new ResponseEntity<WeatherDto>(dto,HttpStatus.OK);
		}
		catch(Exception e) {
			return new ResponseEntity<>(e.getMessage(),HttpStatus.BAD_REQUEST);
		}
	}
	
	@GetMapping("/all")
	public ResponseEntity<?> getAll(){
		try {
			List<WeatherDto> allWeather = i.getAllWeather();
			return new ResponseEntity<List<WeatherDto>>(allWeather,HttpStatus.OK);
		}
		catch(Exception e) {
			 return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
		
	}
	
   
	@GetMapping("/getOne/{id}")
	public ResponseEntity<?> getOne(@PathVariable Long id){
		try {
		WeatherDto weatherById = i.getWeatherById(id);
		return new ResponseEntity<WeatherDto>(weatherById,HttpStatus.OK);
	  }
		catch(Exception e) {
			 return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
       }
    }
 
	@PutMapping("/update/{id}")
	public ResponseEntity<?> update(@PathVariable Long id,@RequestBody WeatherDto dto){
		try {
			WeatherDto updateWeather = i.updateWeather(id, dto);
			return new ResponseEntity<WeatherDto>(updateWeather,HttpStatus.OK);
		}
		catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
		
	}
	
	@PatchMapping("/partialUpdate/{id}")
	public ResponseEntity<?> partialUpdate(@PathVariable Long id,@RequestBody WeatherDto dto){
		try {
			WeatherDto partialUpdate = i.partialUpdate(id, dto);
			return new ResponseEntity<WeatherDto>(partialUpdate,HttpStatus.OK);
		}
		catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
	}
    	
	@DeleteMapping("/delete/{id}")
	public ResponseEntity<?> delete(@PathVariable Long id){
		try {
		    i.deleteWeatherHistory(id);
		    return new ResponseEntity<>("Deleted record with ID : " + id, HttpStatus.NO_CONTENT);
		    
		}
    catch (Exception e) {
        return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
	}
	
	}
}