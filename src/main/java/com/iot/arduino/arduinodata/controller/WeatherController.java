package com.iot.arduino.arduinodata.controller;

import com.iot.arduino.arduinodata.entity.Forecast;
import com.iot.arduino.arduinodata.entity.HourlyData;
import com.iot.arduino.arduinodata.service.WeatherDataService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.List;

@RequestMapping("weather")
@RestController
public class WeatherController {

    private static final Logger logger = LogManager.getLogger();

    @Autowired
    WeatherDataService weatherDataService;


    @GetMapping("getcurrent")
    public String getCurrentWeather() {

        HourlyData hourlyDataForTime = weatherDataService.getHourlyDataForTime(new Date());

        StringBuilder sb = new StringBuilder();

        if (hourlyDataForTime != null) {
            sb.append("Tmp:").append(hourlyDataForTime.getTemperature()).append(hourlyDataForTime.getTemperatureUnit());
            sb.append(" Wind:").append(hourlyDataForTime.getWindSpeed()).append("-").append(hourlyDataForTime.getWindDirection());
            sb.append(" - ").append(hourlyDataForTime.getShortForecast());
        } else {
            sb.append("Weather data not available.");
        }

        return sb.toString();
    }

    @GetMapping("forecast")
    public String getLatestForecast() {

        List<Forecast> latestForecastData = weatherDataService.getLatestForecastData();

        logger.info("latestForecastData : {}", latestForecastData);

        StringBuilder sb = new StringBuilder();

        if (!latestForecastData.isEmpty()) {

            for (int i = 0; i < latestForecastData.size(); i++) {
                Forecast forecast = latestForecastData.get(i);
                sb.append(forecast.getName()).append(":").append(forecast.getShortForecast());
                if (i < latestForecastData.size() - 1) sb.append(", ");
            }
        } else {
            sb.append("Forecast data not available.");
        }

        return sb.toString();
    }

}
