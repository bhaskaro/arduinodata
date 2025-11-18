package com.iot.arduino.arduinodata.service;

import com.iot.arduino.arduinodata.entity.Forecast;
import com.iot.arduino.arduinodata.entity.HourlyData;
import com.iot.arduino.arduinodata.repository.ForecastDataRepository;
import com.iot.arduino.arduinodata.repository.HourlyDataRepository;
import com.iot.arduino.arduinodata.utils.WeatherUtils;
import jakarta.transaction.Transactional;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.util.Streamable;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class WeatherDataService {

    private static final Logger logger = LogManager.getLogger();

    @Autowired
    HourlyDataRepository hourlyDataRepository;

    @Autowired
    ForecastDataRepository forecastDataRepository;

    @Autowired
    WeatherUtils weatherUtils;

    public void updateHourlyData() {

        HourlyData[] hourlyData = weatherUtils.getHourlyWeather();

        Optional<HourlyData> findTopByOrderByUpdatedTimeDesc = hourlyDataRepository.findTopByOrderByUpdatedTimeDesc();

        logger.info("findTopByOrderByUpdatedTimeDesc : {}", findTopByOrderByUpdatedTimeDesc);
        logger.info("hourlyData[0].getUpdateTime : {}", hourlyData[0].getUpdatedTime());

        if (hourlyData.length > 2 &&
                (findTopByOrderByUpdatedTimeDesc.isEmpty()
                        || findTopByOrderByUpdatedTimeDesc.get().getUpdatedTime().before(hourlyData[0].getUpdatedTime()))) {

            logger.info("hourly data in the table is empty/expired, updating it now.");
            hourlyDataRepository.deleteAll();

            Arrays.stream(hourlyData).sequential().forEach(hourlyData1 -> {
                logger.info("saving hourly data : {}", hourlyData1);
                hourlyDataRepository.save(hourlyData1);
            });

        } else {
            logger.info("hourly data is already have the latest copy.");
        }
    }

    public List<HourlyData> getHourlyData() {

        return Streamable.of(hourlyDataRepository.findAll()).toList();
    }

    public HourlyData getHourlyDataForTime(Date date) {

        Optional<HourlyData> optional = hourlyDataRepository.findTopByStartTimeLessThanEqualAndEndTimeGreaterThanEqual(date, date);

        logger.info("getHourlyDataForTime : {} is {}", date, optional.orElse(null));
        return optional.orElse(null);
    }

    public List<Forecast> getLatestForecastData() {

        List<Forecast> top2Forecasts = forecastDataRepository.findTop2ByEndTimeGreaterThanEqualOrderByStartTimeAsc(new Date());

        logger.info("top2Forecasts : {}", top2Forecasts);

        return top2Forecasts;
    }

    public void updateForecastData() {

        Forecast[] forecasts = weatherUtils.getForecast();

        for (Forecast forecast : forecasts)
            logger.info("forecast from REST : {}", forecast);

        List<Forecast> top2Forecasts = forecastDataRepository.findTop2ByEndTimeGreaterThanEqualOrderByStartTimeAsc(new Date());

        top2Forecasts.forEach(forecast -> logger.info("top 2 Forecasts from DB: {}", forecast));

        if (forecasts.length > 2 &&
                (top2Forecasts.isEmpty()
                        || top2Forecasts.get(0).getUpdatedTime().before(forecasts[0].getUpdatedTime()))) {

            logger.info("forecast data in the table is empty/expired, updating it now.");
            forecastDataRepository.deleteAll();

            for (Forecast forecast : forecasts) {
                logger.info("saving forecast data : {}", forecast);
                forecastDataRepository.save(forecast);
            }

        } else {
            logger.info("forecast data is already upto date, not updating. source : {}, new {}", top2Forecasts.get(0).getUpdatedTime(), forecasts[0].getUpdatedTime());
        }
    }

}
