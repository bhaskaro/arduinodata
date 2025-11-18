package com.iot.arduino.arduinodata.schdl;

import com.iot.arduino.arduinodata.service.WeatherDataService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

@Component
public class ArdDataScheduler {

    private static final Logger logger = LogManager.getLogger();

    @Autowired
    WeatherDataService weatherDataService;

    @Scheduled(fixedRateString = "${weather.hourly.refresh.interval.secs}", timeUnit = TimeUnit.SECONDS)
    public void refreshHourlyData() {

        logger.info("refreshHourlyData invoked");
        weatherDataService.updateHourlyData();
    }

    @Scheduled(fixedRateString = "${weather.forecast.refresh.interval.secs}", timeUnit = TimeUnit.SECONDS)
    public void refreshForecastData() {

        logger.info("refreshForecastData invoked");
        weatherDataService.updateForecastData();
    }

}
