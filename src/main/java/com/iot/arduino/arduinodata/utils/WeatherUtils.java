package com.iot.arduino.arduinodata.utils;

import com.iot.arduino.arduinodata.entity.Forecast;
import com.iot.arduino.arduinodata.entity.HourlyData;
import com.iot.arduino.arduinodata.excep.RTException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;

import static org.apache.http.client.utils.DateUtils.parseDate;

@Component
public class WeatherUtils {

    private static final Logger logger = LogManager.getLogger();

    @Value("${weather.hourly.api}")
    private String hourlyApi;

    @Value("${weather.forecast.api}")
    private String forecastApi;

    @Value("${weather.date.patterns}")
    private String[] datePatterns;

//    @Autowired
//    HttpUtils httpUtils;

    public HourlyData[] getHourlyWeather() {

        HttpUtils httpUtils = new HttpUtils();
        String hourlyDataStr = httpUtils.get(hourlyApi, null);
        logger.info("hourlyDataStr -- > {}", hourlyDataStr);

        return parseHourlyData(hourlyDataStr);
    }

    private HourlyData[] parseHourlyData(String jsonData) {

        HourlyData[] hourlyData;
        JSONParser jsonParser = new JSONParser();

        try {
            JSONObject root = (JSONObject) jsonParser.parse(jsonData);

            logger.info("root -- > {}", root);

            JSONObject properties = (JSONObject) root.get("properties");

            Date updateTime = parseDate((String) properties.get("updateTime"), datePatterns);

            JSONArray periods = (JSONArray) properties.get("periods");
            hourlyData = new HourlyData[periods.size()];

            logger.info("periods -- > {}", periods);

            if (logger.isDebugEnabled())
                periods.stream().forEach(period -> {
                    logger.debug("period -- > {}", period);
                });

            for (int i = 0; i < periods.size(); i++) {

                JSONObject period = (JSONObject) periods.get(i);
                hourlyData[i] = new HourlyData();

                hourlyData[i].setTemperature((Long) period.get("temperature"));
                hourlyData[i].setTemperatureUnit((String) period.get("temperatureUnit"));

                hourlyData[i].setDaytime((Boolean) period.get("isDaytime"));

                hourlyData[i].setShortForecast((String) period.get("shortForecast"));
                hourlyData[i].setDetailedForecast((String) period.get("detailedForecast"));

                hourlyData[i].setWindSpeed((String) period.get("windSpeed"));
                hourlyData[i].setWindDirection((String) period.get("windDirection"));

                hourlyData[i].setStartTime(parseDate((String) period.get("startTime"), datePatterns));
                hourlyData[i].setEndTime(parseDate((String) period.get("endTime"), datePatterns));

                hourlyData[i].setUpdatedTime(updateTime);
            }

        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new RTException(e);
        }


        return hourlyData;
    }

    public Forecast[] getForecast() {

        HttpUtils httpUtils = new HttpUtils();
        String forecastStr = httpUtils.get(forecastApi, null);
        logger.info("forecastStr -- > {}", forecastStr);

        return parseForecast(forecastStr);
    }

    private Forecast[] parseForecast(String jsonData) {

        Forecast[] forecasts;
        JSONParser jsonParser = new JSONParser();

        try {
            JSONObject root = (JSONObject) jsonParser.parse(jsonData);

            logger.info("root -- > {}", root);

            JSONObject properties = (JSONObject) root.get("properties");

            Date updateTime = parseDate((String) properties.get("updateTime"), datePatterns);

            JSONArray periods = (JSONArray) properties.get("periods");
            forecasts = new Forecast[periods.size()];

            logger.info("periods -- > {}", periods);

            if (logger.isDebugEnabled())
                periods.stream().forEach(period -> {
                    logger.debug("period -- > {}", period);
                });

            for (int i = 0; i < periods.size(); i++) {

                JSONObject period = (JSONObject) periods.get(i);
                forecasts[i] = new Forecast();

                forecasts[i].setName((String) period.get("name"));
                forecasts[i].setTemperature((Long) period.get("temperature"));
                forecasts[i].setTemperatureUnit((String) period.get("temperatureUnit"));

                forecasts[i].setDaytime((Boolean) period.get("isDaytime"));

                forecasts[i].setShortForecast((String) period.get("shortForecast"));
                forecasts[i].setDetailedForecast((String) period.get("detailedForecast"));

                forecasts[i].setWindSpeed((String) period.get("windSpeed"));
                forecasts[i].setWindDirection((String) period.get("windDirection"));

                forecasts[i].setStartTime(parseDate((String) period.get("startTime"), datePatterns));
                forecasts[i].setEndTime(parseDate((String) period.get("endTime"), datePatterns));

                forecasts[i].setUpdatedTime(updateTime);
            }

        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new RTException(e);
        }

        return forecasts;
    }
}
